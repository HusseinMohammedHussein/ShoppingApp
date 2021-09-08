package com.e.commerce.data.model.auth.favorite

import android.os.Parcelable
import com.e.commerce.data.model.product.ProductPojo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


// Created by Hussein Mohammed on 9/7/2021.
@Parcelize
data class FavoritesDataPojo(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("total") @Expose val total: Int,
    @SerializedName("product") @Expose val product: ProductPojo,
    @SerializedName("data") @Expose val favoriteProductsData: ArrayList<FavoriteDataPojo>
) : Parcelable {
    @Parcelize
    data class FavoriteDataPojo(
        @SerializedName("id") @Expose val id: Int,
        @SerializedName("product") @Expose val product: ProductPojo
    ) : Parcelable
}
