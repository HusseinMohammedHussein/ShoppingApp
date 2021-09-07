package com.e.commerce.data.model

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// Created by Hussein_Mohammad on 5/2/2021.

@Parcelize
data class ProductPojo(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("discount") @Expose val discount: Int,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("image") @Expose val image: String,
    @SerializedName("images") @Expose val images: ArrayList<String>,
    @SerializedName("description") @Expose val description: String,
    @SerializedName("price") @Expose val price: Double,
    @SerializedName("old_price") @Expose val old_price: Double,
    @SerializedName("in_cart") @Expose val in_cart: Boolean,
    @SerializedName("in_favorites") @Expose val in_favorites: Boolean
): Parcelable
