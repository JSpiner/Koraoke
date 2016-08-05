package net.jspiner.koraoke;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Copyright 2016 JSpiner. All rights reserved.
 *
 * @author JSpiner (jspiner@naver.com)
 * @project Android
 * @since 2016. 8. 5.
 */
public class Util {


    //context
    public static Context context;

    //텍스트 파일 불러오기
    public static String readText( String fileName) {


        StringBuilder sb = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));

            String mLine = null;
            mLine = reader.readLine();
            while (mLine != null) {
                sb.append(mLine);
                sb.append("\n");
                mLine = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();

        /*
        String text;
        try {
            InputStream is = context.getAssets().open(file);

            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            text = new String(buffer);
        }
        catch (Exception e){
            text = "error" + e.getMessage();
            e.printStackTrace();
        }
        return text;*/
    }
}
