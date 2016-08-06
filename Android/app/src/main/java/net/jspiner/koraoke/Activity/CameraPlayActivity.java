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
 * @since 2016. 8. 6.
 */
public class CameraPlayActivity extends AppCompatActivity {

    //로그에 쓰일 tag
    public static final String TAG = CameraPlayActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    void init(){
        ButterKnife.bind(this);
    }
}
