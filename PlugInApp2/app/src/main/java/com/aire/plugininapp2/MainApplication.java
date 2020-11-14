package com.aire.plugininapp2;

import android.app.Application;
import android.content.Context;

import java.io.File;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class MainApplication extends Application {
    private DexClassLoader mPluginClassLoader;
    private Application mPluginApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mPluginApplication.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 整个的流程为 plugin.apk在assets目录下 拷贝到files/plugin.apk文件中
        mPluginClassLoader = Utils.extractApk(this, "plugin.apk");
        mPluginApplication = Utils.getPluginApplication(this);
        File apkFile = getFileStreamPath("plugin.apk"); // files目录下
        File dexFile = getFileStreamPath("plugin.dex");
        PatchClassLoaderHelper.patchClassLoader((PathClassLoader) getClassLoader(), apkFile, dexFile);
        ResourceHelper.reloadResources(getBaseContext());
    }

    public DexClassLoader getPluginClassLoader() {
        return mPluginClassLoader;
    }

    public Application getPluginApplication() {
        return mPluginApplication;
    }
}
