package com.e.commerce.ui.fragments.user.notifyservice

import com.e.commerce.data.model.auth.NotificationsPojo
import com.e.commerce.data.remote.retrofit.ApiControl
import retrofit2.Call

// Created by Hussein_Mohammad on 6/25/2021.
class NotificationsRepo {
    fun getNotifications(): Call<NotificationsPojo> = ApiControl.apiService().getNotifies()
}