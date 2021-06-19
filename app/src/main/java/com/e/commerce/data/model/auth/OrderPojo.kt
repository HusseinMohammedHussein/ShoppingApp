package com.e.commerce.data.model.auth

import android.os.Parcelable
import com.e.commerce.data.model.auth.common.CommonResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// Created by Hussein_Mohammad on 5/29/2021.

@Parcelize
data class OrderPojo(
    @SerializedName("status") @Expose val status: Boolean,
    @SerializedName("message") @Expose val message: String,
    @SerializedName("data") @Expose val data: OrdersPojo
) : Parcelable {
    @Parcelize
    data class OrdersPojo(
        @SerializedName("data") @Expose val ordersList: MutableList<OrderItemPojo>,
        @SerializedName("total") @Expose val total: Int?
    ) : Parcelable {
        @Parcelize
        data class OrderItemPojo(
            @SerializedName("id") @Expose val id: Int?,
            @SerializedName("total") @Expose val total: Double,
            @SerializedName("date") @Expose val date: String,
            @SerializedName("status") @Expose val status: String
        ) : Parcelable
    }

    data class OrderResponsePojo(
        private val mStatus: Boolean, private val mMessage: String,
        @SerializedName("data") @Expose val data: OrderResponseDataPojo
    ) : CommonResponse(mStatus, mMessage) {

        data class OrderResponseDataPojo(
            @SerializedName("id") @Expose val id: Int?,
            @SerializedName("payment_method") @Expose val payment_method: String,
            @SerializedName("promo_code") @Expose val promo_code: String,
            @SerializedName("cost") @Expose val cost: Double,
            @SerializedName("vat") @Expose val vat: Double,
            @SerializedName("discount") @Expose val discount: Double,
            @SerializedName("total") @Expose val total: Double
        )
    }

    data class AddOrderPojo(
        @SerializedName("address_id") @Expose val addressId: Int?,
        @SerializedName("payment_method") @Expose val paymentMethod: Int?, // 1 = cash, 2 = Online
        @SerializedName("use_points") @Expose val usePoints: Boolean?,
        @SerializedName("promo_code_id") @Expose val promoCodeId: Int?
    )
}