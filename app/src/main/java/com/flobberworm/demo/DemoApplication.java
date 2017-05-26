package com.flobberworm.demo;

import android.app.Application;

/**
 * Created by Kornan on 2017/5/26.
 */

public class DemoApplication extends Application {
    public static DemoApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }
}
