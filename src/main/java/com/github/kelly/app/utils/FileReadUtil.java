package com.github.kelly.app.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileReadUtil {

    public static byte[] readFileFromClasspath(String filepath) {

        try (final InputStream inputStream = FileReadUtil.class.getClassLoader().getResourceAsStream(filepath)) {
            if (inputStream != null) {
                final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                final byte[] buffer = new byte[100000];
                int readBytes;
                while ((readBytes = inputStream.read(buffer)) != -1) {
                    baos.write(buffer, 0, readBytes);
                }
                return baos.toByteArray();
            } // if

            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
