package com.aire.pluginbundle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class PluginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("zimotag", "PluginActivity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin1);
    }
}