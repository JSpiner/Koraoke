package net.jspiner.koraoke.Activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import net.jspiner.koraoke.Adapter.MainPagerAdapter;
import net.jspiner.koraoke.Fragment.MainCategoryFragment;
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
public class MainActivity extends AppCompatActivity {

    //로그에 쓰일 tag
    public static final String TAG = MainActivity.class.getSimpleName();


    //toolbar
    @Bind(R.id.toolbar)
    Toolbar toolbar;

//    @Bind(R.id.tv_toolbar_title)
//    TextView tvTitle;

    @Bind(R.id.htab_viewpager)
    ViewPager pager;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    void init(){
        ButterKnife.bind(this);

        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new MainCategoryFragment(),"인기차트");
        adapter.addFrag(new MainCategoryFragment(), "최신곡");
        adapter.addFrag(new MainCategoryFragment(), "ㄱㄴㄷ순 정렬");
        adapter.addFrag(new MainCategoryFragment(), "기타");
        pager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.htab_tabs);
        tabLayout.setupWithViewPager(pager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

        NestedScrollView scrollView = (NestedScrollView) findViewById (R.id.nested_scrollview);
        scrollView.setFillViewport(true);

        initToolbar();
    }


    //actionbar 설정
    void initToolbar(){

//        tvTitle.setText("고라오케");
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,toolbar,R.string.app_name,R.string.app_name){

            @Override
            public void onDrawerOpened(View drawerView) {
                Log.d(TAG, "drawer opened");
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                Log.d(TAG,"drawer closed");
                super.onDrawerClosed(drawerView);

            }

        };

        drawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

    }
}
