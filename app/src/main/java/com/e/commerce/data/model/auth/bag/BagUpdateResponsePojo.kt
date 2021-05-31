package com.e.commerce.data.model.auth.bag

import com.e.commerce.data.model.auth.common.BagItemResponsePojo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Created by Hussein_Mohammad on 5/19/2021.

data class BagUpdateResponsePojo(
    @SerializedName("status") @Expose val status: Boolean,
    @SerializedName("message") @Expose val message: String,
    @SerializedName("data") @Expose val data: BagResponsePojo
) {
    data class BagResponsePojo(
        @SerializedName("cart") @Expose val cart: BagItemResponsePojo,
        @SerializedName("sub_total") @Expose val sub_total: Int,
        @SerializedName("total") @Expose val total: Int
    )
}
