package com.aire.plugin.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aire.plugin.mypluginlib.IBean;
import com.aire.plugin.mypluginlib.ICallback;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    public void clickMe(final View view) {
        try {
            Class<?> beanClazz = PluginManager.getInstance(this).getClassLoader()
                    .loadClass("com.aire.plugin.pluginbundle.Bean");
            IBean bean = (IBean) beanClazz.newInstance();
            bean.setName("zpp");
            bean.registerCallback(new ICallback() {
                @Override
                public void sendResult(String result) {
                    Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("zimotag", e.getMessage());
        }
    }
}