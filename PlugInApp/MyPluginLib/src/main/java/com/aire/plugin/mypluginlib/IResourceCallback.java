package com.aire.plugin.mypluginlib;

import android.content.Context;
import android.graphics.drawable.Drawable;

public interface IResourceCallback {
    String getStringForResId(Context context);
    Drawable getDrawableForResId(Context context);
}
