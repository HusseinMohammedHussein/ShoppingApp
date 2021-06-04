package com.e.commerce.ui.fragments.auth.profile

import com.e.commerce.data.model.ProfilePojo
import com.e.commerce.data.model.auth.AddressPojo
import com.e.commerce.data.model.auth.OrderPojo
import com.e.commerce.data.remote.retrofit.ApiControl
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Call

// Created by Hussein_Mohammad on 5/14/2021.
@ViewModelScoped
class ProfileRepo {

    fun getProfile(): Call<ProfilePojo> {
        return ApiControl.apiService().getProfile()
    }

    fun getTotalOrders(): Call<OrderPojo> {
        return ApiControl.apiService().getOrders()
    }

    fun getAddress(): Call<AddressPojo> {
        return ApiControl.apiService().getAddresses()
    }
}