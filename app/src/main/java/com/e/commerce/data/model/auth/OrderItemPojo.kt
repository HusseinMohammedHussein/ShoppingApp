package com.e.commerce.data.model.auth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Created by Hussein_Mohammad on 5/29/2021.

data class OrderPojo(
    @SerializedName("status") @Expose val status: Boolean,
    @SerializedName("message") @Expose val message: String,
    @SerializedName("data") @Expose val data: OrdersPojo
) {
    data class OrdersPojo(
        @SerializedName("data") @Expose val ordersList: MutableList<OrderItemPojo>,
        @SerializedName("total") @Expose val total: Int
    ) {
        data class OrderItemPojo (
            @SerializedName("id") @Expose val id: Int,
            @SerializedName("total") @Expose val total: Double,
            @SerializedName("date") @Expose val date: String,
            @SerializedName("status") @Expose val status: String
        )
    }
}