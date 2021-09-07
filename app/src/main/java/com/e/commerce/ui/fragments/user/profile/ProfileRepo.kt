package com.e.commerce.ui.fragments.user.profile

import com.e.commerce.data.model.ProfilePojo
import com.e.commerce.data.model.auth.AddressPojo
import com.e.commerce.data.model.auth.LogoutPojo
import com.e.commerce.data.model.auth.OrderPojo
import com.e.commerce.data.remote.retrofit.ApiControl
import retrofit2.Call

// Created by Hussein_Mohammad on 5/14/2021.
class ProfileRepo {

    fun getProfile(): Call<ProfilePojo> = ApiControl.apiService().getProfile()

    fun getTotalOrders(): Call<OrderPojo> = ApiControl.apiService().getOrders()

    fun getAddress(): Call<AddressPojo> = ApiControl.apiService().getAddresses()

    fun logout(fcmToken: String): Call<LogoutPojo> = ApiControl.apiService().logout(fcmToken)
}