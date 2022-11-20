package com.nhk.thesis.service.interfaces;

import java.io.IOException;
import java.io.InputStream;

public interface Test {

    public void extractDataFromPDF(InputStream inputFile) throws IOException;
}
