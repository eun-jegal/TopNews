package com.example.topnews.ui

import android.app.Application
import com.example.topnews.ui.di.*

class App: Application() {
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}