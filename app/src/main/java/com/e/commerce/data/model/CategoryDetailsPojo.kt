package com.e.commerce.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Created by Hussein_Mohammad on 5/9/2021.

data class CategoryDetailsPojo(
    @SerializedName("data") @Expose val data: Data
) {
    data class Data(
        @SerializedName("data") @Expose val Products: MutableList<ProductPojo>
    )
}