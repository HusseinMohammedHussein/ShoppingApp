package com.e.commerce.data.model.auth.order

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


// Created by Hussein Mohammed on 9/7/2021.
data class OrderProductsPojo(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("quantity") @Expose val quantity: Int,
    @SerializedName("price") @Expose val price: Int,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("image") @Expose val image: String
)
