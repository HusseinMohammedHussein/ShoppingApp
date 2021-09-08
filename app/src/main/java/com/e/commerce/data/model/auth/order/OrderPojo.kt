package com.e.commerce.data.model.auth.order

import android.os.Parcelable
import com.e.commerce.data.model.common.CommonResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// Created by Hussein_Mohammad on 5/29/2021.

@Parcelize
data class OrderPojo(
    val mStatus: Boolean, val mMessage: String,
    @SerializedName("data") @Expose val orderData: OrdersPojo
) : Parcelable, CommonResponse(mStatus, mMessage) {

    @Parcelize
    data class OrdersPojo(
        @SerializedName("data") @Expose val ordersData: ArrayList<OrderItemPojo>,
        @SerializedName("total") @Expose val total: Int
    ) : Parcelable
}