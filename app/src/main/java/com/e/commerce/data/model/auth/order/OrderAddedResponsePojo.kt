package com.e.commerce.data.model.auth.order

import com.e.commerce.data.model.common.CommonResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Created by Hussein Mohammed on 9/7/2021.
data class OrderAddedResponsePojo(
    private val mStatus: Boolean, private val mMessage: String,
    @SerializedName("data") @Expose val orderAddedData: OrderAddedResponseDataPojo
) : CommonResponse(mStatus, mMessage) {

    data class OrderAddedResponseDataPojo(
        @SerializedName("id") @Expose val id: Int,
        @SerializedName("payment_method") @Expose val paymentMethod: String,
        @SerializedName("promo_code") @Expose val promoCode: String,
        @SerializedName("cost") @Expose val cost: Double,
        @SerializedName("vat") @Expose val vat: Double,
        @SerializedName("discount") @Expose val discount: Double,
        @SerializedName("total") @Expose val total: Double
    )
}
