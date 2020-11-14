package com.aire.plugininapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startPluginService(View view) {
        Intent serviceIntent = new Intent();
        String className = "com.aire.pluginbundle.MyService";
        serviceIntent.setClassName(this, className);
        startService(serviceIntent);
    }

    public void startPluginActivity(View view) {
        Intent activityIntent = new Intent();
        String className = "com.aire.pluginbundle.PluginActivity";
        activityIntent.setClassName(this, className);
        startActivity(activityIntent);
    }
}