package com.e.commerce.ui.fragments.user.checkout

import com.e.commerce.data.model.auth.order.AddOrderPojo
import com.e.commerce.data.model.auth.order.OrderAddedResponsePojo
import com.e.commerce.data.remote.retrofit.ApiControl
import retrofit2.Call

// Created by Hussein_Mohammad on 6/17/2021.
class CheckoutRepo {
    fun addOrder(order: AddOrderPojo): Call<OrderAddedResponsePojo> {
        return ApiControl.apiService().addOrder(order)
    }
}