package com.e.commerce.data.model.auth.order

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


// Created by Hussein Mohammed on 9/7/2021.
data class AddOrderPojo(
    @SerializedName("address_id") @Expose val addressId: Int,
    @SerializedName("payment_method") @Expose val paymentMethod: Int, // 1 = cash, 2 = Online
    @SerializedName("use_points") @Expose val usePoints: Boolean?,
    @SerializedName("promo_code_id") @Expose val promoCodeId: Int
)
