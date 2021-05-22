package com.e.commerce.data.model.home

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Created by Hussein_Mohammad on 5/2/2021.

data class BannerPojo(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("image") @Expose val image: String,
    @SerializedName("category") @Expose val category: Category,
) {
    data class Category(
        @SerializedName("id") @Expose val id: Int,
        @SerializedName("image") @Expose val image: String,
        @SerializedName("name") @Expose val name: String
    )
}
