package com.e.commerce.data.model.home

import com.e.commerce.data.model.ProductPojo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Created by Hussein_Mohammad on 5/2/2021.

data class HomePojo(
    @SerializedName("data") @Expose val homeData: HomeData
){
    data class HomeData(
        @SerializedName("banners") @Expose val banners: MutableList<BannerPojo>,
        @SerializedName("products") @Expose val products: MutableList<ProductPojo>,
    )
}
