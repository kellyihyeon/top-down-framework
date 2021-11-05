package com.github.kelly.app.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileReadUtil {

    public static byte[] readFileFromClasspath(String filepath) {
//        Logger logger = LoggerFactory.getLogger(FileReadUtil.class);

//        InputStream inputStream = null;
        try (final InputStream inputStream = FileReadUtil.class.getClassLoader().getResourceAsStream(filepath)) {
//            inputStream = FileReadUtil.class.getClassLoader().getResourceAsStream(filepath);    // hello.html
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
