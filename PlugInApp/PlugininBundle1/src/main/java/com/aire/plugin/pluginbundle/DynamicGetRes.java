package com.aire.plugin.pluginbundle;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.aire.plugin.mypluginlib.IResourceCallback;

public class DynamicGetRes implements IResourceCallback {
    @Override
    public String getStringForResId(Context context) {
        return context.getResources().getString(R.string.plugin_bundle_1);
    }

    @Override
    public Drawable getDrawableForResId(Context context) {
        return context.getResources().getDrawable(R.drawable.plugin_drawable);
    }
}
