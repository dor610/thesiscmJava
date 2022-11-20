package com.nhk.thesis.util;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.oauth.DbxCredential;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.*;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import com.dropbox.core.v2.users.FullAccount;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class DropboxUtils {

    private static final String ACCESS_TOKEN = "sl.BTXuMVtwhbumF49h7voKK07sN4tHUFpnK-NWVXxbOr02eVLjU3q1eP4dbPGgVOBcdmxgCUpMcNgbhnn3xyn1OcPp7kJO1Qro_TKut31YePaE6XSmzQLLyKwX9Al1nNGdK7vosmYgIa4";
    private static final String REFRESH_TOKEN = "GOJb19kDgDIAAAAAAAAAAV6_tvS_8zAtYhM-9XvZBzXPEhOT14WI0ejkQ2e4mwuv";
    private static final String dropboxAppKey = "53oo6zq88dlh1kx";
    private static final String dropboxAppSecret = "u22jruqrva0gb65";
    private static DbxClientV2 client;

    public DropboxUtils() throws DbxException {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("thesiscm").build();
        DbxCredential credentials = new DbxCredential(ACCESS_TOKEN, -1L,REFRESH_TOKEN, dropboxAppKey, dropboxAppSecret);
        DropboxUtils.client = new DbxClientV2(config, credentials);
        FullAccount account = client.users().getCurrentAccount();
    }

    public Map<String, String> uploadFile(InputStream in, String fileName) throws UploadErrorException, DbxException, IOException{
        String name = fileName.replaceAll("\\s+", "_");
        String path = "/" + name;
        System.out.println(DropboxUtils.client);
        System.out.println(DropboxUtils.client.files());
        FileMetadata metadata = DropboxUtils.client.files().uploadBuilder(path).uploadAndFinish(in);

        SharedLinkMetadata sharedLink = client.sharing().createSharedLinkWithSettings(path);
        String url = sharedLink.getUrl() /*convertToRAWLink(sharedLink.getUrl())*/;
        Map<String, String> map = new HashMap<>();
        map.put("url", convertToDownloadLink(url));
        map.put("viewUrl", convertToRAWLink(url));
        map.put("sharedUrl", url);
        map.put("path", path);

        return map;
    }

    public void getFiles() throws ListFolderErrorException, DbxException{
        ListFolderResult result = client.files().listFolder("");
        //client.files().deleteV2("/")
        while (true){
            for(Metadata metadata : result.getEntries()){
                System.out.println(metadata.getPathLower());
            }
            if(!result.getHasMore()){
                System.out.println("There is no more file!");
                break;
            }

            result = client.files().listFolderContinue(result.getCursor());
        }
    }

    public void deleteFile(String path) throws DbxException {
        DropboxUtils.client.files().deleteV2(path);
    }

    public String convertToRAWLink(String sharedLink){
        String rawLink = sharedLink.substring(0, sharedLink.length()-4)+"raw=1";
        return rawLink;
    }

    public String convertToDownloadLink(String sharedLink){
        String rawLink = sharedLink.replace("www.dropbox.com", "dl.dropboxusercontent.com");
        return rawLink;
    }
}
