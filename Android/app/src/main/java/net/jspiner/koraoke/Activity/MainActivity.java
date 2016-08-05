package net.jspiner.koraoke.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.jspiner.koraoke.R;

import butterknife.ButterKnife;

/**
 * Copyright 2016 JSpiner. All rights reserved.
 *
 * @author JSpiner (jspiner@naver.com)
 * @project Android
 * @since 2016. 8. 5.
 */
public class MainActivity extends AppCompatActivity {

    //로그에 쓰일 tag
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        init();
    }

    void init(){
        ButterKnife.bind(this);
    }
}
