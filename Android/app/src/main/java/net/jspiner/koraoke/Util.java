package net.jspiner.koraoke;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import net.jspiner.koraoke.Model.HttpService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import retrofit.Endpoint;
import retrofit.RestAdapter;
import retrofit.client.Response;

/**
 * Copyright 2016 JSpiner. All rights reserved.
 *
 * @author JSpiner (jspiner@naver.com)
 * @project Android
 * @since 2016. 8. 5.
 */
public class Util {

    //로그에 쓰일 tag
    public static final String TAG = Util.class.getSimpleName();

    //context
    public static Context context;

    //앱내에서 모든 http 통신은 httpservice로 동작
    private static HttpService httpService;

    //http통신시 endPoint를 이 변수로 변경, ex) endPoint.setPort("80");
    public static FooEndPoint endPoint;

    //HttpService의 EndPoint
    public static class FooEndPoint implements Endpoint {
        private static final String BASE = context.getResources().getString(R.string.API_SERVER);

        private String url = BASE;

        public void setPort(String port) {
            url = BASE +":"+ port;
        }

        @Override
        public String getName() {
            return "default";
        }

        @Override
        public String getUrl() {
            Log.d(TAG, "url : " + url);
            if (url == null) setPort("80");
            return url;
        }
    }

    //Singleton Endpoint
    public static FooEndPoint getEndPoint(){
        if(endPoint==null){
            endPoint = new FooEndPoint();
        }
        return endPoint;
    }

    //Singleton HttpService
    public static HttpService getHttpSerivce() {
        if(httpService==null) {

            RestAdapter restAdapter =
                    new RestAdapter.Builder()
                            .setEndpoint(getEndPoint())
                            .build();
            httpService = restAdapter.create(HttpService.class);
        }
        return httpService;
    }

    //retrofit response를 string으로 변경
    public static String getStringfromReponse(Response response){
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {

            reader = new BufferedReader(new InputStreamReader(response.getBody().in()));

            String line;

            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        String result = sb.toString();
        return  result;
    }

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

    }
}
