package com.e.commerce.data.model.auth.register

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


// Created by Hussein Mohammed on 9/8/2021.
data class RegisterDataPojo(
    @SerializedName("name") @Expose val username: String,
    @SerializedName("phone") @Expose val phone: String,
    @SerializedName("email") @Expose val email: String,
    @SerializedName("password") @Expose val password: String
)
