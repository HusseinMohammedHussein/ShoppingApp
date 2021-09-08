package com.e.commerce.data.model.auth.login

import com.e.commerce.data.model.common.CommonResponse
import com.e.commerce.data.model.common.SignResponsePojo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Created by Hussein_Mohammad on 6/15/2021.
data class LoginPojo(
    private val mMessage: String, private val mStatus: Boolean,
    @SerializedName("data") @Expose val loginData: SignResponsePojo
) : CommonResponse(mStatus, mMessage)
