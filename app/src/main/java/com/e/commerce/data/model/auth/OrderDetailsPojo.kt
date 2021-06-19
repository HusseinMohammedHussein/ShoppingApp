package com.e.commerce.data.model.auth

import com.e.commerce.data.model.auth.AddressPojo.AddressDataPojo.AddressObjectPojo
import com.e.commerce.data.model.auth.common.CommonResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Created by Hussein_Mohammad on 6/5/2021.
data class OrderDetailsPojo(
    val mStatus: Boolean, val mMessage: String,
    @SerializedName("data") @Expose val data: OrderDetailsDataPojo
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
        @SerializedName("address") @Expose val address: AddressObjectPojo,
        @SerializedName("products") @Expose val products: MutableList<OrderProductsPojo>
    )

    data class OrderProductsPojo(
        @SerializedName("id") @Expose val id: Int,
        @SerializedName("quantity") @Expose val quantity: Int,
        @SerializedName("price") @Expose val price: Int,
        @SerializedName("name") @Expose val name: String,
        @SerializedName("image") @Expose val image: String
    )
}
