package com.mailapp.sourceop;


import com.mailapp.exceptions.ErrorResponseCodeException;
import com.mailapp.sourceprovider.ISource;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpStreamOP implements ISource {

    @Override
    public InputStream getInputStream(String path) throws Exception {

        InputStream in = null;

        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        int code = conn.getResponseCode();
        if (code == 200) {
            in = conn.getInputStream();
        } else {
            throw new ErrorResponseCodeException("error response code is" + code);
        }
        return in;
    }
}

