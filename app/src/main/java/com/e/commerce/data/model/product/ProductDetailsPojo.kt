package com.e.commerce.data.model.product

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Created by Hussein_Mohammad on 5/11/2021.
data class ProductDetailsPojo(
    @SerializedName("data") @Expose val productDetailsData: ProductPojo
)
