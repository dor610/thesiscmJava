package com.nhk.thesis.service.implement;

import com.nhk.thesis.service.interfaces.Test;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

@Service
public class TestImpl implements Test {
    @Override
    public void extractDataFromPDF(InputStream inputFile) throws IOException {
        PDDocument document = PDDocument.load(inputFile);

        document.getClass();

        if (!document.isEncrypted()) {

            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            stripper.setSortByPosition(true);

            PDFTextStripper tStripper = new PDFTextStripper();

            String pdfFileInText = tStripper.getText(document);
            //System.out.println("Text:" + st);

            // split by whitespace
            String lines[] = pdfFileInText.split("\\r?\\n");
            for (String line : lines) {
                if(Pattern.compile("^\\d+").matcher(line.substring(0,1)).matches()){
                    System.err.println(line);
                }else{
                    System.out.println(line);
                }
            }

        }
    }
}
