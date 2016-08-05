package net.jspiner.koraoke.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.jspiner.koraoke.R;
import net.jspiner.koraoke.Util;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Copyright 2016 JSpiner. All rights reserved.
 *
 * @author JSpiner (jspiner@naver.com)
 * @project Android
 * @since 2016. 8. 6.
 */
public class SignupPagerAdapter extends PagerAdapter {

    LayoutInflater inflater;

    Context context;

    public SignupPagerAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = null;

        view = inflater.inflate(R.layout.item_signup_page1 + position, null);

        switch (position){
            case 0:
                ViewBinder1 binder1 = new ViewBinder1(view);
                binder1.tvPrivacy1.setText((Util.readText("privacy1.txt")));
                break;
            case 1:
                break;
            case 2:
                break;
        }

        container.addView(view);


        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);

    }


    @Override
    public boolean isViewFromObject(View v, Object obj) {
        return v == obj;
    }

    class ViewBinder1{

        @Bind(R.id.tv_signup_privacy1)
        TextView tvPrivacy1;

        public ViewBinder1(View view){
            ButterKnife.bind(this, view);
        }

    }
}
