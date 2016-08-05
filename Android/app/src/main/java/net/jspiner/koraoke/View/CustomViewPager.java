package net.jspiner.koraoke.View;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Copyright 2016 JSpiner. All rights reserved.
 *
 * @author JSpiner (jspiner@naver.com)
 * @project Android
 * @since 2016. 8. 6.
 */
public class CustomViewPager extends ViewPager {

    boolean touchAble = true;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTouchable(boolean able) {
        this.touchAble = able;
    }


    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (touchAble) {
            return super.canScroll(v, checkV, dx, x, y);
        }
        else{
            return true;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (touchAble) {
            return super.onInterceptTouchEvent(event);
        } else {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (touchAble) {
            return super.onTouchEvent(event);
        } else {
            return false;
        }
    }
}
