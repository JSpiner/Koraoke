package net.jspiner.koraoke.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import net.jspiner.koraoke.Model.UserModel;
import net.jspiner.koraoke.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Copyright 2016 JSpiner. All rights reserved.
 *
 * @author JSpiner (jspiner@naver.com)
 * @project Koraoke
 * @since 2016. 8. 5.
 */
public class SplashActivity extends AppCompatActivity {

    //로그에 쓰일 tag
    public static final String TAG = SplashActivity.class.getSimpleName();

    @Bind(R.id.btn_splash_login)
    OAuthLoginButton btnOauthLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        init();
    }

    void init(){

        ButterKnife.bind(this);

        initNaverOauthModule();

        //TODO : 자동로그인 처리
    }

    void initNaverOauthModule(){
        OAuthLogin mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(
                SplashActivity.this
                ,getString(R.string.naver_api_client_id)
                ,getString(R.string.naver_api_client_secret)
                ,getString(R.string.naver_api_client_name)
        );

        btnOauthLogin.setOAuthLoginHandler(oAuthLoginHandler);
    }

    OAuthLoginHandler oAuthLoginHandler = new OAuthLoginHandler(){

        @Override
        public void run(boolean b) {
            Log.d(TAG, "login result : " + b);
            if(b){

                Log.d(TAG, "login info : " + OAuthLogin.getInstance().getAccessToken(getBaseContext()));

                String requstStr = OAuthLogin.getInstance().getTokenType(getBaseContext())
                                    + " "
                                    + OAuthLogin.getInstance().getAccessToken(getBaseContext());


                new RequestApiTask().execute();

            }
            else{
                Toast.makeText(getBaseContext(),"로그인을 실패하였습니다.",Toast.LENGTH_LONG).show();
            }
        }
    };


    private class RequestApiTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = "https://openapi.naver.com/v1/nid/getUserProfile.xml";
            String at = OAuthLogin.getInstance().getAccessToken(getBaseContext());
            Pasingversiondata(OAuthLogin.getInstance().requestApi(getBaseContext(), at, url));

            return null;
        }

        protected void onPostExecute(Void content) {

            if (UserModel.getInstance().email == null) {
                Toast.makeText(SplashActivity.this,
                        "로그인 실패하였습니다.  잠시후 다시 시도해 주세요!!", Toast.LENGTH_SHORT)
                        .show();
            } else {

                Toast.makeText(getBaseContext(),
                        UserModel.getInstance().name+"님 환영합니다.",
                        Toast.LENGTH_LONG).show();

                Intent intent = new Intent(SplashActivity.this, SignupActivity.class);
                startActivity(intent);

                finish();
            }

        }

        private void Pasingversiondata(String data) { // xml 파싱
            String f_array[] = new String[9];

            try {
                XmlPullParserFactory parserCreator = XmlPullParserFactory
                        .newInstance();
                XmlPullParser parser = parserCreator.newPullParser();
                InputStream input = new ByteArrayInputStream(
                        data.getBytes("UTF-8"));
                parser.setInput(input, "UTF-8");

                int parserEvent = parser.getEventType();
                String tag;
                boolean inText = false;
                boolean lastMatTag = false;

                int colIdx = 0;

                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                    switch (parserEvent) {
                        case XmlPullParser.START_TAG:
                            tag = parser.getName();
                            if (tag.compareTo("xml") == 0) {
                                inText = false;
                            } else if (tag.compareTo("data") == 0) {
                                inText = false;
                            } else if (tag.compareTo("result") == 0) {
                                inText = false;
                            } else if (tag.compareTo("resultcode") == 0) {
                                inText = false;
                            } else if (tag.compareTo("message") == 0) {
                                inText = false;
                            } else if (tag.compareTo("response") == 0) {
                                inText = false;
                            } else {
                                inText = true;

                            }
                            break;
                        case XmlPullParser.TEXT:
                            tag = parser.getName();
                            if (inText) {
                                if (parser.getText() == null) {
                                    f_array[colIdx] = "";
                                } else {
                                    f_array[colIdx] = parser.getText().trim();
                                }

                                colIdx++;
                            }
                            inText = false;
                            break;
                        case XmlPullParser.END_TAG:
                            tag = parser.getName();
                            inText = false;
                            break;

                    }

                    parserEvent = parser.next();
                }
            } catch (Exception e) {
                Log.e(TAG, "Error in network call", e);
            }
            UserModel.create(f_array);


        }
    }
}
