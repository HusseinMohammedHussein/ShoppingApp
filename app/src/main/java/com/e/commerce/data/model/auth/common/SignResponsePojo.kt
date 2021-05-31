package com.e.commerce.data.model.auth.common

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Created by Hussein_Mohammad on 5/31/2021.
data class SignResponsePojo(
    val mStatus: Boolean, val mMessage: String,
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("name") @Expose val username: String,
    @SerializedName("phone") @Expose val phone: String,
    @SerializedName("email") @Expose val email: String,
    @SerializedName("image") @Expose val image: String,
    @SerializedName("token") @Expose val token: String,
    @SerializedName("points") @Expose val points: Int,
    @SerializedName("credit") @Expose val credit: Double,
) : CommonResponse(mStatus, mMessage)
