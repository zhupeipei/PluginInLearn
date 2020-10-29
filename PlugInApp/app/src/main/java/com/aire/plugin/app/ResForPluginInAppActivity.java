package com.aire.plugin.app;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.aire.plugin.mypluginlib.IResourceCallback;

public class ResForPluginInAppActivity extends AppCompatActivity {
    private AssetManager mAssetManager;
    private Resources mResources;
    private Resources.Theme mTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_for_plugin_in_app);
    }

    @ViewClick
    public void getResStrInPluginApp(View view) {
        loadResources();
        TextView tv = findViewById(R.id.main_id_str_from_plugin_tv);
        try {
            IResourceCallback resourceCallback =
                    (IResourceCallback) PluginManager.getInstance(this).getClassLoader()
                    .loadClass("com.aire.plugin.pluginbundle.DynamicGetRes").newInstance();
            String str = resourceCallback.getStringForResId(this);
            tv.setText(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadResources() {
        mAssetManager = Utils.getAssetManager(this);
        mResources = new Resources(mAssetManager, super.getResources().getDisplayMetrics(), super.getResources().getConfiguration());
        mTheme = mResources.newTheme();
        mTheme.setTo(super.getTheme());
    }

    @Override
    public AssetManager getAssets() {
        if (mAssetManager != null) {
            return mAssetManager;
        }
        return super.getAssets();
    }

    @Override
    public Resources.Theme getTheme() {
        if (mTheme != null) {
            return mTheme;
        }
        return super.getTheme();
    }

    @Override
    public Resources getResources() {
        if (mResources != null) {
            return mResources;
        }
        return super.getResources();
    }

    public void getResDrawableInPluginApp(View view) {
        ImageView iv = findViewById(R.id.main_id_str_from_plugin_iv);
        try {
            IResourceCallback resourceCallback =
                    (IResourceCallback) PluginManager.getInstance(this).getClassLoader()
                            .loadClass("com.aire.plugin.pluginbundle.DynamicGetRes").newInstance();
            Drawable drawable = resourceCallback.getDrawableForResId(this);
            iv.setImageDrawable(drawable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}