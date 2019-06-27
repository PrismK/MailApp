package com.mailapp.sourceprovider;

import java.io.InputStream;

public interface ISource {

    InputStream getInputStream(String path) throws Exception;

}
