package com.aire.pluginbundle;

import android.app.Application;
import android.util.Log;

public class PluginApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("zimotag", "pluginApplication onCreate");
    }
}
