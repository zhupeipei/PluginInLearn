package com.aire.plugin.app;

import android.app.Application;
import android.content.Context;

import dalvik.system.DexClassLoader;

public class PluginManager {
    private static PluginManager sPluginManager;
    private static Context sContext;
    private DexClassLoader mClassLoader;
    private String mApplicationName;
    private Application mPluginApplication;

    public static PluginManager getInstance(Context context) {
        if (sPluginManager == null) {
            sContext = context;
            sPluginManager = new PluginManager();
        }
        return sPluginManager;
    }

    public void loadApk() {
        mClassLoader = Utils.extractApk(sContext, "plugin.apk");
        mApplicationName = Utils.getApplicationName(sContext);
        try {
            mPluginApplication = (Application) mClassLoader.loadClass(mApplicationName).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Application getPluginApplication() {
        return mPluginApplication;
    }

    public DexClassLoader getClassLoader() {
        return mClassLoader;
    }
}
