package com.e.commerce.data.model.auth.register

import com.e.commerce.data.model.common.CommonResponse
import com.e.commerce.data.model.common.SignResponsePojo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Created by Hussein_Mohammad on 5/13/2021.
data class RegisterPojo(
    private val mStatus: Boolean, private val mMessage: String,
    @SerializedName("data") @Expose val registerData: SignResponsePojo
) : CommonResponse(mStatus, mMessage)
