package com.e.commerce.data.model.auth.bag

import android.os.Parcelable
import com.e.commerce.data.model.common.CommonResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// Created by Hussein_Mohammad on 5/19/2021.
@Parcelize
data class BagProductsPojo(
    val mStatus: Boolean, val mMessage: String,
    @SerializedName("data") @Expose val bagProductsDataData: BagProductsDataPojo
) : Parcelable, CommonResponse(mStatus, mMessage) {

    @Parcelize
    data class BagProductsDataPojo(
        @SerializedName("cart_items") @Expose val bagItems: ArrayList<BagItemPojo>,
        @SerializedName("sub_total") @Expose val sub_total: Double,
        @SerializedName("total") @Expose val total: Double
    ) : Parcelable
}
