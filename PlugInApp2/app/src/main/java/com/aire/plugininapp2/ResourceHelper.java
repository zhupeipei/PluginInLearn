package com.aire.plugininapp2;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;

public class ResourceHelper {
    public static Resources sResource;

    public static void reloadResources(Context context) {
        File apkFile = context.getFileStreamPath("plugin.apk");
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            ReflectionUtil.invokeMethod(assetManager, "addAssetPath",
                    new Class[]{String.class}, new Object[]{context.getPackageResourcePath()});
            ReflectionUtil.invokeMethod(assetManager, "addAssetPath",
                    new Class[]{String.class}, new Object[]{apkFile.getAbsolutePath()});

            Resources resources = new Resources(
                    assetManager,
                    context.getResources().getDisplayMetrics(),
                    context.getResources().getConfiguration());

            ReflectionUtil.setField(context, "mResources", resources);
            //这是最主要的需要替换的，如果不支持插件运行时更新，只留这一个就可以了
            Object packageInfo = ReflectionUtil.getFieldObject(context, "mPackageInfo");
            ReflectionUtil.setField(packageInfo, "mResources", resources);

            sResource = resources;
            //需要清理mTheme对象，否则通过inflate方式加载资源会报错
            //如果是activity动态加载插件，则需要把activity的mTheme对象也设置为null
            ReflectionUtil.setField(context, "mTheme", null);

//            Resources.Theme theme = resources.newTheme();
//            theme.setTo(context.getTheme());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
