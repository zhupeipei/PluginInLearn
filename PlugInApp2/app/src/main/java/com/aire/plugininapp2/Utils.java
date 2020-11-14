package com.aire.plugininapp2;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import dalvik.system.DexClassLoader;

public class Utils {
    // 解压assets目录下的
    // assets目录下文件拷贝到files/plugin.apk，解压到app_plugin_dex目录下，通过app_plugin_apk_dex获取到classLoader
    public static DexClassLoader extractApk(@NonNull Context context, @NonNull String apkName) {
        File dstFile = context.getFileStreamPath("plugin.apk"); // files目录下
        copyAssetsFileToAppDataFolder(context, apkName, dstFile);
        File unzipFile = context.getDir("plugin_apk_dex", Context.MODE_PRIVATE); // app_plugin_apk_dex
        DexClassLoader classLoader = new DexClassLoader(
                dstFile.getAbsolutePath(), unzipFile.getAbsolutePath(),
                null, context.getClassLoader());
        return classLoader;
    }

    private static void copyAssetsFileToAppDataFolder(Context context, String assetsFile, File dstFile) {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = context.getAssets().open(assetsFile);
            outputStream = new FileOutputStream(dstFile);
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Application getPluginApplication(MainApplication application) {
        try {
            String applicationName = getApplicationName(application);
            Class<?> clazz = application.getPluginClassLoader().loadClass(applicationName);
            return (Application) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getApplicationName(Context context) {
        try {
            File dexFile = context.getFileStreamPath("plugin.apk");
            Class packageParse = ReflectionUtil.getClass("android.content.pm.PackageParser");
            Object clazzObj = packageParse.newInstance();
            Object packageObj = ReflectionUtil.invokeMethod(clazzObj, "parsePackage",
                    new Class[]{File.class, int.class},
                    new Object[]{dexFile, PackageManager.GET_RECEIVERS});
            Object applicationInfo = ReflectionUtil.getFieldObject(
                    packageObj.getClass(), "applicationInfo", packageObj);
            String applicationName = (String) ReflectionUtil.getFieldObject(
                    applicationInfo.getClass(), "className", applicationInfo);
            Log.d("zimotag", "get plugin applicationName " + applicationName);
            return applicationName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
