package com.e.commerce.data.model.auth

import com.e.commerce.data.model.auth.common.CommonResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

// Created by Hussein_Mohammad on 5/31/2021.
data class SettingProfilePojo(
    @SerializedName("name") @Expose val name: String,
    @SerializedName("phone") @Expose val phone: String,
    @SerializedName("email") @Expose val email: String,
//    @SerializedName("password") @Expose val password: String,
    @SerializedName("image") @Expose val image: String?
)
