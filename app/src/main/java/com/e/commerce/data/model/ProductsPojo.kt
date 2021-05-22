package com.e.commerce.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Created by Hussein_Mohammad on 5/11/2021.
data class ProductsPojo(
    @SerializedName("data") @Expose val data: ProductDataPojo
){
    data class ProductDataPojo(
        @SerializedName("data") @Expose val products: MutableList<ProductPojo>,
        @SerializedName("total") @Expose val total: Int
    )
}
