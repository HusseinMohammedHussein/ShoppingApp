package com.e.commerce

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import timber.log.Timber
import timber.log.Timber.DebugTree

// Created by Hussein_Mohammad on 5/1/2021.

class CommerceApplication : Application() {

    private var timberTree = DebugTree()

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        if (BuildConfig.DEBUG)
            Timber.plant(timberTree)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null

        @JvmName("getContext1")
        fun getContext(): Context? {
            return context
        }
    }
}