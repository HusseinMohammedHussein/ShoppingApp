package com.e.commerce.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.e.commerce.data.model.auth.AddressPojo.AddressDataPojo.AddressObjectPojo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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

    fun setDouble(key: String, value: Double) {
        operation.apply {
            putFloat(key, value.toFloat())
            apply()
        }
    }

    fun setAddressGson(key: String, objectPojo: AddressObjectPojo?) {
        operation.apply {
            val gson = Gson()
            if (objectPojo != null) {
                val json = gson.toJson(objectPojo)
                putString(key, json)
            } else {
                putString(key, null)
            }
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

    fun getAddressGson(key: String): AddressObjectPojo? {
        val gson = Gson()
        val json = preferences.getString(key, null)
        val type = object : TypeToken<AddressObjectPojo>() {}.type
        return if (json != null) gson.fromJson(json.toString(), type) else null
    }

    fun getString(key: String): String? = preferences.getString(key, "")

    fun getInt(key: String): Int = preferences.getInt(key, 0)

    fun getDouble(key: String): Double = preferences.getFloat(key, 0F).toDouble()

    fun getBoolean(key: String): Boolean = preferences.getBoolean(key, false)

    fun getLong(key: String): Long = preferences.getLong(key, 0)

    fun clear() = operation.clear().apply()
}