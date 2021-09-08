package com.e.commerce.data.model.auth.bag

import com.e.commerce.data.model.common.CommonResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Created by Hussein_Mohammad on 5/19/2021.

data class BagUpdatePojo(
    val mStatus: Boolean, val mMessage: String,
    @SerializedName("data") @Expose val bagUpdateData: BagDataPojo
) : CommonResponse(mStatus, mMessage) {
    data class BagDataPojo(
        @SerializedName("cart") @Expose val bag: BagItemPojo,
        @SerializedName("sub_total") @Expose val subTotal: Double,
        @SerializedName("total") @Expose val total: Double
    )
}
