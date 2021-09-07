package com.e.commerce.ui.fragments.user.orderDetails

import com.e.commerce.data.model.auth.OrderDetailsPojo
import com.e.commerce.data.model.auth.PromocodePojo
import com.e.commerce.data.remote.retrofit.ApiControl
import retrofit2.Call

// Created by Hussein_Mohammad on 6/5/2021.
class OrderDetailsRepo {
    fun getOrderDetails(orderId: Int): Call<OrderDetailsPojo> {
        return ApiControl.apiService().getOrderDetails(orderId)
    }

    fun getPromocodePercentage(code: String) : Call<PromocodePojo> {
        return  ApiControl.apiService().checkPromoCode(code)
    }
}