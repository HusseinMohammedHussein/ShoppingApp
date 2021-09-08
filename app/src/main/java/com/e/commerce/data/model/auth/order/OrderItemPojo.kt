package com.e.commerce.data.model.auth.order

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


// Created by Hussein Mohammed on 9/7/2021.
@Parcelize
data class OrderItemPojo(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("total") @Expose val total: Double,
    @SerializedName("date") @Expose val date: String,
    @SerializedName("status") @Expose val status: String
) : Parcelable
