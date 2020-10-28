package com.example.myapplication.main

import android.app.Application

class MainApplication : Application() {

    companion object {
        var application: Application? = null
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }
}