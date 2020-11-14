package com.aire.plugininapp2;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends Activity {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    public Resources getResources() {
        if (ResourceHelper.sResource != null) {
            return ResourceHelper.sResource;
        }
        return super.getResources();
    }

    @Override
    public Resources.Theme getTheme() {
        return super.getTheme();
    }

    @Override
    public AssetManager getAssets() {
        return super.getAssets();
    }
}
