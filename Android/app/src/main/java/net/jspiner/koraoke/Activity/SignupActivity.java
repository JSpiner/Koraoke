package net.jspiner.koraoke.Activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import net.jspiner.koraoke.Adapter.SignupPagerAdapter;
import net.jspiner.koraoke.R;
import net.jspiner.koraoke.View.CustomViewPager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Bind(R.id.pager_signup)
    CustomViewPager pagerSignup;

    View[] viewIndicator;

    int nowPage = 0;

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


        viewIndicator = new View[3];
        for (int i = 0; i < 3; i++) {
            viewIndicator[i] = new View(getBaseContext());
            int dimen = 50;
            int margin = 50;
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(dimen, dimen);
            lp.setMargins(i == 0 ? 0 : margin, 0, 0, 0);
            viewIndicator[i].setLayoutParams(lp);
            viewIndicator[i].setBackgroundResource(R.drawable.indicator_circle);
            viewIndicator[i].setSelected(i == 0);
            linearIndicator.addView(viewIndicator[i]);
        }

//         pagerSignup.setTouchable(false);
        pagerSignup.setAdapter(new SignupPagerAdapter(this));
        pagerSignup.setTouchable(false);

        pagerSignup.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                nowPage = position;

                for(int i=0;i<3;i++){
                    viewIndicator[i].setSelected(false);
                }
                viewIndicator[nowPage].setSelected(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.imv_signup_back)
    void onSignupBackClick(){
        if(nowPage!=0){
            pagerSignup.setCurrentItem(nowPage-1);
        }
    }

    @OnClick(R.id.fab_signup_next)
    void onSignupNextClick(){
        if(nowPage!=2){
            pagerSignup.setCurrentItem(nowPage+1);
        }
        else{
            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

}
