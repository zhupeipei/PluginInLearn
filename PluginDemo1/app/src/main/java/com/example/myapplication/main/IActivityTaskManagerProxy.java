package com.example.myapplication.main;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

import com.example.myapplication.sub.SubActivity;
import com.example.myapplication.util.ReflectionUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class IActivityTaskManagerProxy implements InvocationHandler {
    Object mInstance;

    public IActivityTaskManagerProxy(Object instance) {
        this.mInstance = instance;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.d("zimotag", "invoke: " + method.getName());
        if (method.getName().equals("startActivity")) {
            Log.d("zimotag", args.toString());
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                if (arg instanceof Intent) {
                    Intent intent = new Intent(MainApplication.Companion.getApplication(), SubActivity.class);
                    intent.putExtra(AppConstants.KEY_RAW_INTENT, (Intent) arg);
                    args[i] = intent;
                    break;
                }
            }
        }
        return method.invoke(mInstance, args);
    }
}