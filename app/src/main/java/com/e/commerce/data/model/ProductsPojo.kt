package com.e.commerce.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// Created by Hussein_Mohammad on 5/11/2021.
@Parcelize
data class ProductsPojo(
    @SerializedName("status") @Expose val status: Boolean,
    @SerializedName("message") @Expose val message: String,
    @SerializedName("data") @Expose val data: ProductDataPojo
) : Parcelable {
    @Parcelize
    data class ProductDataPojo(
        @SerializedName("data") @Expose val products: MutableList<ProductPojo>,
        @SerializedName("total") @Expose val total: Int
    ) : Parcelable
}
