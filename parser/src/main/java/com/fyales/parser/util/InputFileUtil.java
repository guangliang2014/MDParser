package com.fyales.parser.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author fyales
 * @since date 10/02/15
 */
public class InputFileUtil {
    //读取文件
    public static String read(String filename) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(filename));

        String s;
        StringBuilder sb = new StringBuilder();

        while ((s = in.readLine()) != null) {
            sb.append(s + "\n");
        }

        in.close();
        return sb.toString();
    }

    //读取流文件
    public static String read(InputStream stream) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] s = new byte[1024];
        int len;
        while((len = stream.read(s)) != -1){
            output.write(s,0,len);
        }
        byte[] outputBytes = output.toByteArray();
        stream.close();
        output.close();
        return new String(outputBytes);
    }
}