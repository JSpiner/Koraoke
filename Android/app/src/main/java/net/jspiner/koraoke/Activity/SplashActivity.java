package net.jspiner.koraoke.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import net.jspiner.koraoke.R;

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

                Log.d(TAG,"login info : "+OAuthLogin.getInstance().getAccessToken(getBaseContext()));

                Intent intent = new Intent(SplashActivity.this, SignupActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(getBaseContext(),"로그인을 실패하였습니다.",Toast.LENGTH_LONG).show();
            }
        }
    };
}
