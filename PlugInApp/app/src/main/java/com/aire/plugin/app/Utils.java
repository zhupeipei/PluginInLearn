package com.aire.plugin.app;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import dalvik.system.DexClassLoader;

public class Utils {
    public static DexClassLoader extractApk(Context context, String apkName) {
        File dstApkFile = context.getFileStreamPath("dex"); // /data/user/0/com.aire.plugin.app/files/dex
        Log.d("zimotag", "extractApk: " + dstApkFile);
        copyAssetFileTo(context, apkName, dstApkFile.getAbsolutePath());
        File fileRelease = context.getDir("dex", 0); // /data/user/0/com.aire.plugin.app/app_dex
        Log.d("zimotag", "extractApk: " + fileRelease);
        DexClassLoader classLoader = new DexClassLoader(dstApkFile.getAbsolutePath(),
                fileRelease.getAbsolutePath(), null, context.getClassLoader());
        return classLoader;
    }

    public static void copyAssetFileTo(Context context, String srcFile, String dstFile) {
        try {
            InputStream inputStream = context.getAssets().open(srcFile);
            try {
                FileOutputStream outputStream = new FileOutputStream(dstFile);
                try {
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buf)) > 0) {
                        outputStream.write(buf, 0, len);
                    }
                } finally {
                    outputStream.close();
                }
            } finally {
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getApplicationName(Context context) {
        // 这里直接解析已经从assets目录解析完成的dex
        File dstApkFile = context.getFileStreamPath("dex"); // /data/user/0/com.aire.plugin.app/files/dex
        try {
            Class packageParserClazz = ReflectionUtil.getClass("android.content.pm.PackageParser");
            Object packageObj = ReflectionUtil.invokeMethod(
                    packageParserClazz,
                    "parsePackage",
                    new Class[]{File.class, int.class},
                    new Object[]{dstApkFile, PackageManager.GET_RECEIVERS});
            Object applicationInfo = ReflectionUtil.getFieldObject(packageObj.getClass(), "applicationInfo", packageObj);
            String applicationName = (String) ReflectionUtil.getFieldObject(applicationInfo.getClass(), "className", applicationInfo);
            return applicationName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
