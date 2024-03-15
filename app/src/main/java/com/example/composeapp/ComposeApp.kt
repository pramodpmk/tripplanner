package com.example.composeapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ComposeApp : Application() {

    var userId: String = ""

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        var instance: ComposeApp? = null
    }
}