package com.e.commerce.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// Created by Hussein_Mohammad on 5/8/2021.

@Parcelize
data class ShopCategoryPojo(
    @SerializedName("data") @Expose val data: Data
) : Parcelable {
    @Parcelize
    data class Data(
        @SerializedName("data") @Expose val categories: MutableList<ShopCategoriesPojo>
    ): Parcelable {
        @Parcelize
        data class ShopCategoriesPojo(
            @SerializedName("id") @Expose val id: Int,
            @SerializedName("name") @Expose val name: String,
            @SerializedName("image") @Expose val image: String
        ): Parcelable
    }
}
