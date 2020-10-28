package com.example.myapplication.main

import android.app.Instrumentation
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.util.ReflectionUtil
import com.example.myapplication.plugin.MainActivity2
import java.lang.reflect.Proxy

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hookStartActivity()
        hookStartActivity2()
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "abc", Toast.LENGTH_LONG).show()
    }

    private fun hookStartActivity() {
        var className: String = "android.app.ActivityTaskManager";
        var fieldName: String = "IActivityTaskManagerSingleton";
        val ActivityTaskManagerClazz: Class<*> =
            ReflectionUtil.getClass(className)
        val IActivityTaskManagerSingleton =
            ReflectionUtil.getFieldObject(
                className,
                fieldName,
                null
            )

        className = "android.util.Singleton"
        fieldName = "mInstance";
        val mInstanceField = ReflectionUtil.getField(
            className,
            fieldName
        )
        val mInstance =
            ReflectionUtil.getFieldObject(
                className,
                fieldName,
                IActivityTaskManagerSingleton
            )

        val proxy: Any = Proxy.newProxyInstance(
            ActivityTaskManagerClazz.classLoader,
            mInstance.javaClass.interfaces,
            IActivityTaskManagerProxy(mInstance)
        )
        ReflectionUtil.setField(
            mInstanceField,
            IActivityTaskManagerSingleton,
            proxy
        )
    }

    private fun hookStartActivity2() {
        val sCurrentActivityThread = ReflectionUtil.getFieldObject("android.app.ActivityThread",
            "sCurrentActivityThread", null)
        val mInstrumentaion = ReflectionUtil.getFieldObject("android.app.ActivityThread",
            "mInstrumentation", sCurrentActivityThread)
        ReflectionUtil.setField("mInstrumentation", sCurrentActivityThread, InstrumentaionProxy(
            mInstrumentaion as Instrumentation?
        ))
    }

    fun click1(view: View) {
        startActivity(Intent(this, MainActivity2::class.java))
    }
}