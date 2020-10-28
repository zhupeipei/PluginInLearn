package com.example.myapplication.main;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;

public class InstrumentaionProxy extends Instrumentation {
    private Instrumentation mOriginInstrumentation;

    public InstrumentaionProxy(Instrumentation mOriginInstrumentation) {
        this.mOriginInstrumentation = mOriginInstrumentation;
    }

    @Override
    public Activity newActivity(ClassLoader cl, String className, Intent intent) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Log.d("zimotag", "newActivity ");
        Parcelable rawIntent = intent.getParcelableExtra(AppConstants.KEY_RAW_INTENT);
        if (rawIntent instanceof Intent) {
            return super.newActivity(cl, ((Intent) rawIntent).getComponent().getClassName(), (Intent) rawIntent);
        }
        return super.newActivity(cl, className, intent);
    }
}
