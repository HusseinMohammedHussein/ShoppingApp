package com.e.commerce.data.model.auth

import android.os.Parcelable
import com.e.commerce.data.model.ProductPojo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// Created by Hussein_Mohammad on 5/16/2021.
@Parcelize
data class FavoritePojo(
    @SerializedName("status") @Expose val status: Boolean,
    @SerializedName("message") @Expose var message: String,
    @SerializedName("data") @Expose val data: FavoriteResponse
) : Parcelable {
    @Parcelize
    data class FavoriteResponse(
        @SerializedName("id") @Expose val id: Int,
        @SerializedName("total") @Expose val total: Int,
        @SerializedName("product") @Expose val product: ProductPojo,
        @SerializedName("data") @Expose val productsFavorites: MutableList<FavoritesResponse>
    ) : Parcelable {
        @Parcelize
        data class FavoritesResponse(
            @SerializedName("id") @Expose val id: Int,
            @SerializedName("product") @Expose val product: ProductPojo
        ) : Parcelable
    }
}
