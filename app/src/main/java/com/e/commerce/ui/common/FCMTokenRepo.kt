package com.e.commerce.ui.common

import com.e.commerce.data.model.auth.FCMTokenPojo
import com.e.commerce.data.remote.retrofit.ApiControl
import retrofit2.Call

// Created by Hussein_Mohammad on 6/25/2021.
class FCMTokenRepo {
    fun setFCMToken(token: String): Call<FCMTokenPojo> = ApiControl.apiService().setFCMToken(token)
}