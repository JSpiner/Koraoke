package net.jspiner.koraoke.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
                String policyText = (Util.readText("privacy1.txt"));
                binder1.tvPrivacy1.setText(Html.fromHtml(policyText));
                binder1.tvPrivacy2.setText(Html.fromHtml(policyText));
                break;
            case 1:
                break;
            case 2:
                ViewBinder3 binder3 = new ViewBinder3(view);
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

        @Bind(R.id.tv_signup_privacy2)
        TextView tvPrivacy2;

        public ViewBinder1(View view){
            ButterKnife.bind(this, view);
        }

    }

    class ViewBinder2{

        public ViewBinder2(View view){
            ButterKnife.bind(this, view);
        }

    }

    class ViewBinder3{

        @Bind(R.id.spn_signup_college)
        Spinner spnColege1;

        @Bind(R.id.spn_signup_college2)
        Spinner spnColege2;

        int[] collegeResources = {
                R.array.ku_subcollege_1,
                R.array.ku_subcollege_2,
                R.array.ku_subcollege_3,
                R.array.ku_subcollege_4,
                R.array.ku_subcollege_5,
                R.array.ku_subcollege_6,
                R.array.ku_subcollege_7,
                R.array.ku_subcollege_8,
                R.array.ku_subcollege_9,
                R.array.ku_subcollege_10,
                R.array.ku_subcollege_11,
                R.array.ku_subcollege_12,
                R.array.ku_subcollege_13,
                R.array.ku_subcollege_14,
                R.array.ku_subcollege_15,
                R.array.ku_subcollege_16,
                R.array.ku_subcollege_17,
        };

        public ViewBinder3(View view){
            ButterKnife.bind(this, view);

            spnColege1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);

                    ArrayAdapter<String> spinnerAdapter
                            = new ArrayAdapter<String>(
                            context,
                            android.R.layout.simple_spinner_item,
                            context.getResources().getStringArray(collegeResources[position])
                    );
                    spinnerAdapter.setDropDownViewResource(
                            android.R.layout.simple_spinner_dropdown_item);
                    spnColege2.setAdapter(spinnerAdapter);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spnColege2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

    }
}
