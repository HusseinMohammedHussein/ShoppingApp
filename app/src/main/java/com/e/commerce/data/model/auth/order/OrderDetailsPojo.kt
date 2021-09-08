package com.e.commerce.data.model.auth.order

import com.e.commerce.data.model.auth.address.AddressesDataPojo.AddressDataPojo
import com.e.commerce.data.model.common.CommonResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Created by Hussein_Mohammad on 6/5/2021.
data class OrderDetailsPojo(
    val mStatus: Boolean, val mMessage: String,
    @SerializedName("data") @Expose val orderDetailsData: OrderDetailsDataPojo
) : CommonResponse(mStatus, mMessage) {

    data class OrderDetailsDataPojo(
        @SerializedName("id") @Expose val id: Int,
        @SerializedName("cost") @Expose val cost: Int,
        @SerializedName("discount") @Expose val discount: Int,
        @SerializedName("points") @Expose val points: Double,
        @SerializedName("vat") @Expose val vat: Double,
        @SerializedName("total") @Expose val total: Double,
        @SerializedName("points_commission") @Expose val points_commission: Double,
        @SerializedName("promo_code") @Expose val promo_code: String,
        @SerializedName("payment_method") @Expose val payment_method: String,
        @SerializedName("date") @Expose val date: String,
        @SerializedName("status") @Expose val status: String,
        @SerializedName("address") @Expose val address: AddressDataPojo,
        @SerializedName("products") @Expose val orderProducts: ArrayList<OrderProductsPojo>
    )
}
