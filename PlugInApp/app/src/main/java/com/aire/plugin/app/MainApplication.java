package com.aire.plugin.app;

import android.app.Application;
import android.content.Context;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PluginManager.getInstance(this).getPluginApplication().onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        PluginManager.getInstance(base).loadApk();
    }
}
