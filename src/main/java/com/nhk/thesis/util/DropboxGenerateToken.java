package com.nhk.thesis.util;

import com.dropbox.core.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class DropboxGenerateToken {
    public static void main(String[] args) throws IOException, DbxException {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("myexamplefolder").build();

        String dropboxAppKey = "53oo6zq88dlh1kx"; // Put your Dropbox App Key here
        String dropboxAppSecret = "u22jruqrva0gb65"; // Put your Dropbox App Secret here

        DbxAppInfo appInfo = new DbxAppInfo(dropboxAppKey, dropboxAppSecret);

        DbxAuthFinish authFinish = scopeAuthorize(appInfo, config);

        System.out.println("Authorization complete.");
        System.out.println("- User ID: " + authFinish.getUserId());
        System.out.println("- Account ID: " + authFinish.getAccountId());
        System.out.println("- Access Token: " + authFinish.getAccessToken());
        System.out.println("- Expires At: " + authFinish.getExpiresAt());
        System.out.println("- Refresh Token: " + authFinish.getRefreshToken());
        System.out.println("- Scope: " + authFinish.getScope());
    }

    private static DbxAuthFinish scopeAuthorize(DbxAppInfo appInfo, DbxRequestConfig requestConfig) throws IOException, DbxException {
        DbxWebAuth webAuth = new DbxWebAuth(requestConfig, appInfo);

        DbxWebAuth.Request webAuthRequest = DbxWebAuth.newRequestBuilder()
                .withNoRedirect()
                .withTokenAccessType(TokenAccessType.OFFLINE)
                // Define here the scopes that you need in your application - and the app created in Dropbox has
                .withScope(Arrays.asList("files.content.read", "files.content.write", "files.metadata.write", "sharing.write"))
                .withIncludeGrantedScopes(IncludeGrantedScopes.USER)
                .build();

        String authorizeUrl = webAuth.authorize(webAuthRequest);
        System.out.println("1. Go to " + authorizeUrl);
        System.out.println("2. Click \"Allow\" (you might have to log in first).");
        System.out.println("3. Copy the authorization code.");
        System.out.print("Enter the authorization code here: ");

        String code = new BufferedReader(new InputStreamReader(System.in)).readLine();
        if (code == null) {
            System.exit(1);
        }
        code = code.trim();

        DbxAuthFinish authFinish = webAuth.finishFromCode(code);

        System.out.println("Successfully requested scope " + authFinish.getScope());
        return authFinish;
    }
}
