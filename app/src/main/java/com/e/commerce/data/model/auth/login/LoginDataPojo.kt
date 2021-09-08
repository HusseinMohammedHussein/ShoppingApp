package com.e.commerce.data.model.auth.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Created by Hussein Mohammed on 9/7/2021.

data class LoginDataPojo(
    @SerializedName("email") @Expose val email: String,
    @SerializedName("password") @Expose val password: String
)
