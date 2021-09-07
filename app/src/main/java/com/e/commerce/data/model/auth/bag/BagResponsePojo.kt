package com.e.commerce.data.model.auth.bag

import android.os.Parcelable
import com.e.commerce.data.model.auth.common.BagItemResponsePojo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// Created by Hussein_Mohammad on 5/19/2021.
@Parcelize
data class BagResponsePojo(
    @SerializedName("status") @Expose val status: Boolean,
    @SerializedName("message") @Expose val message: String,
    @SerializedName("data") @Expose val bagResponseData: BagResponsePojo
) : Parcelable {

    @Parcelize
    data class BagResponsePojo(
        @SerializedName("cart_items") @Expose val cartItems: ArrayList<BagItemResponsePojo>,
        @SerializedName("sub_total") @Expose val sub_total: Double,
        @SerializedName("total") @Expose val total: Double
    ) : Parcelable
}
