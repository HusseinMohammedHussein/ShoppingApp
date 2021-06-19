package com.e.commerce.data.model.auth

import com.e.commerce.data.model.auth.common.CommonResponse
import com.e.commerce.data.model.auth.common.SignResponsePojo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Created by Hussein_Mohammad on 6/15/2021.
data class LoginPojo(
    private val mMessage: String, private val mStatus: Boolean,
    @SerializedName("data") @Expose val data: SignResponsePojo
) : CommonResponse(mStatus, mMessage) {

    data class LoginDataPojo(
        @SerializedName("email") @Expose val email: String,
        @SerializedName("password") @Expose val password: String
    )
}
