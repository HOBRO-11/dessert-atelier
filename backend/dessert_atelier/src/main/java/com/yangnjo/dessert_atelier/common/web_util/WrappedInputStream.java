package com.yangnjo.dessert_atelier.common.web_util;

import java.io.IOException;
import java.io.InputStream;

public abstract class WrappedInputStream extends InputStream {

    WrappedInputStream() {
    }

    @Override
    public void close() throws IOException {
    }

    public void closeQuietly() {
        try {
            super.close();
        } catch (IOException e) {
            // ignore
        }
    }

}
