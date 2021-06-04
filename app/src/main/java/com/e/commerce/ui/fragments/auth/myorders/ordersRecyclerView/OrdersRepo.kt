package com.e.commerce.ui.fragments.auth.myorders.ordersRecyclerView

import com.e.commerce.data.model.auth.OrderPojo
import com.e.commerce.data.remote.retrofit.ApiControl
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Call

// Created by Hussein_Mohammad on 6/3/2021.
@ViewModelScoped
class OrdersRepo {
    fun getOrders(): Call<OrderPojo> {
        return ApiControl.apiService().getOrders()
    }
}