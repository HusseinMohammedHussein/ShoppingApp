package com.e.commerce.ui.fragments.user.checkout

import com.e.commerce.data.model.auth.OrderPojo
import com.e.commerce.data.model.auth.OrderPojo.AddOrderPojo
import com.e.commerce.data.remote.retrofit.ApiControl
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Call

// Created by Hussein_Mohammad on 6/17/2021.
@ViewModelScoped
class CheckoutRepo {
    fun addOrder(order: AddOrderPojo): Call<OrderPojo.OrderResponsePojo> {
        return ApiControl.apiService().addOrder(order)
    }
}