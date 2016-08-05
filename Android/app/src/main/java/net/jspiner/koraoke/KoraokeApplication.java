package net.jspiner.koraoke;

import android.app.Application;

/**
 * Copyright 2016 JSpiner. All rights reserved.
 *
 * @author JSpiner (jspiner@naver.com)
 * @project Android
 * @since 2016. 8. 6.
 */
public class KoraokeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Util.context = getBaseContext();

    }

}
