package com.e.commerce.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

// Created by Hussein_Mohammad on 5/14/2021.
@SuppressLint("CommitPrefEdits")
class SharedPref constructor(context: Context) {
    private var preferences: SharedPreferences

    private val PREFS_NAME = "users_kotlin"
    private val MODE = Context.MODE_PRIVATE
    private var operation: SharedPreferences.Editor

    init {
        preferences = context.getSharedPreferences(PREFS_NAME, MODE)
        operation = preferences.edit()
    }

    fun setString(key: String, value: String) {
        operation.apply {
            putString(key, value)
            apply()
        }
    }

    fun setInt(key: String, value: Int) {
        operation.apply {
            putInt(key, value)
            apply()
        }
    }

    fun setBoolean(key: String, value: Boolean) {
        operation.apply {
            putBoolean(key, value)
            apply()
        }
    }

    fun setLong(key: String, value: Long) {
        operation.apply {
            putLong(key, value)
            apply()
        }
    }

    fun getString(key: String): String? = preferences.getString(key, "")

    fun getInt(key: String): Int = preferences.getInt(key, 0)

    fun getBoolean(key: String): Boolean = preferences.getBoolean(key, false)

    fun getLong(key: String): Long = preferences.getLong(key, 0)

    fun clear() = operation.clear().apply()
}