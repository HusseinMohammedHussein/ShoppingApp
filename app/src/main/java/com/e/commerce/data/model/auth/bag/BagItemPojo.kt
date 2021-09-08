package com.e.commerce.data.model.auth.bag

import android.os.Parcelable
import com.e.commerce.data.model.product.ProductPojo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// Created by Hussein_Mohammad on 5/19/2021.
@Parcelize
data class BagItemPojo(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("quantity") @Expose var productItemQuantity: Int,
    @SerializedName("product") @Expose val product: ProductPojo
) : Parcelable
