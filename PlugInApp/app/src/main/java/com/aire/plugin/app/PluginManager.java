package com.aire.plugin.app;

import android.app.Application;
import android.content.Context;

import dalvik.system.DexClassLoader;

public class PluginManager {
    private static PluginManager sPluginManager;
    private Context mContext;
    private DexClassLoader mClassLoader;
    private String mApplicationName;
    private Application mPluginApplication;

    public static PluginManager getInstance(Context context) {
        if (sPluginManager == null) {
            sPluginManager = new PluginManager();
            sPluginManager.mContext = context;
        }
        return sPluginManager;
    }

    /**
     * 加载apk
     */
    public void loadApk() {
        mClassLoader = Utils.extractApk(mContext, "plugin.apk");
        mApplicationName = Utils.getApplicationName(mContext);
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
