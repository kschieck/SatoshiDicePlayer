package com.satoshidice.kyle.satoshidiceplayer.android.login;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Kyle on 19/11/2014.
 * Version: 1
 */
public class FileAdapter {

    public static String loadFileData(Context context, String fileName) throws IOException {
        FileInputStream fis = null;

        try {
            fis = context.openFileInput(fileName);
            int c;
            StringBuilder sb = new StringBuilder();
            while((c = fis.read()) != -1) {
                sb.append(Character.toString((char)c));
            }

            return sb.toString();

        } finally {
            //Silently close
            if (fis != null) { try { fis.close(); } catch (Exception e) { /* Nothing */ } }
        }
    }

    public static void saveFileData(Context context, String data, String fileName) throws IOException {
        FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        fos.write(data.getBytes());
        fos.close();
    }

}
