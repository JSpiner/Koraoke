package net.jspiner.koraoke;

import android.content.Context;

import java.io.InputStream;

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
    public static String readText( String file) {
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
        return text;
    }
}
