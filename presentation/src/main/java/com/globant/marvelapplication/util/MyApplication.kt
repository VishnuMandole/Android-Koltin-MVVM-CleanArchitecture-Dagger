package com.marvel.marvelapplication.util

import android.app.Application
import com.marvel.marvelapplication.di.ApplicationComponent
import com.marvel.marvelapplication.di.DaggerApplicationComponent


class MyApplication : Application() {
    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.builder().application(this).build()
    }


}