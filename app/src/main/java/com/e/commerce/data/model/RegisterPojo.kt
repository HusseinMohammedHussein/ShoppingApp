package com.e.commerce.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Created by Hussein_Mohammad on 5/13/2021.
data class RegisterPojo(
    @SerializedName("status") @Expose val status: Boolean,
    @SerializedName("message") @Expose val message: String,
    @SerializedName("data") @Expose val data: ResponseRegisterPojo
) {
    data class ResponseRegisterPojo(
        @SerializedName("id") @Expose val id: Int,
        @SerializedName("name") @Expose val username: String,
        @SerializedName("phone") @Expose val phone: String,
        @SerializedName("email") @Expose val email: String,
        @SerializedName("image") @Expose val image: String,
        @SerializedName("token") @Expose val token: String,
    )

    data class RequestRegisterPojo(
        @SerializedName("name") @Expose val username: String,
        @SerializedName("phone") @Expose val phone: String,
        @SerializedName("email") @Expose val email: String,
        @SerializedName("password") @Expose val password: String
    )
}
