package com.e.commerce.data.model.home

import com.e.commerce.data.model.ProductPojo
import com.e.commerce.data.model.auth.common.CommonResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Created by Hussein_Mohammad on 5/2/2021.

data class HomePojo(
    private val mStatus: Boolean, private val mMessage: String,
    @SerializedName("data") @Expose val homeData: HomeData
) : CommonResponse(mStatus, mMessage) {
    data class HomeData(
        @SerializedName("banners") @Expose val banners: ArrayList<BannerPojo>,
        @SerializedName("products") @Expose val products: ArrayList<ProductPojo>,
    )
}
