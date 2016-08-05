package net.jspiner.koraoke.Activity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import net.jspiner.koraoke.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Copyright 2016 JSpiner. All rights reserved.
 *
 * @author JSpiner (jspiner@naver.com)
 * @project Android
 * @since 2016. 8. 5.
 */
public class SignupActivity extends AppCompatActivity {

    //로그에 쓰일 tag
    public static final String TAG = SignupActivity.class.getSimpleName();

    @Bind(R.id.fab_signup_next)
    FloatingActionButton fabNext;

    @Bind(R.id.linear_indicator)
    LinearLayout linearIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        init();
    }

    void init(){
        ButterKnife.bind(this);

        fabNext.setBackgroundTintList(
                ColorStateList.valueOf(
                        getResources().getColor(R.color.colorAccent)
                )
        );


        for (int i = 0; i < 3; i++) {
            View view = new View(getBaseContext());
            int dimen = 50;
            int margin = 50;
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(dimen, dimen);
            lp.setMargins(i == 0 ? 0 : margin, 0, 0, 0);
            view.setLayoutParams(lp);
            view.setBackgroundResource(R.drawable.indicator_circle);
            view.setSelected(i == 0);
            linearIndicator.addView(view);
        }
    }


}
