/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author sp
 */
public class SPHttpClient {

    public String getContentFromUrl(String url) throws Exception {
        URL localURL;
        localURL = new URL(url);
        URLConnection connection = localURL.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
        httpURLConnection.setRequestProperty("Accept-Charset", "gbk");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-javascript");

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuilder resultBuffer = new StringBuilder();
        String tempLine = null;

        if (httpURLConnection.getResponseCode() != 200) {
            System.out.println("fetch stock from url: " + url + " failed");
        }
        inputStream = httpURLConnection.getInputStream();
        inputStreamReader = new InputStreamReader(inputStream, "gbk");
        reader = new BufferedReader(inputStreamReader);

        while ((tempLine = reader.readLine()) != null) {
            resultBuffer.append(tempLine);
        }
        reader.close();
        inputStreamReader.close();
        inputStream.close();
        return resultBuffer.toString();
    }
}
