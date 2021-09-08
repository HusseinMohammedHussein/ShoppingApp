package com.e.commerce.data.model.auth

import com.e.commerce.data.model.common.CommonResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Created by Hussein_Mohammad on 6/25/2021.
data class LogoutPojo(
    private val mStatus: Boolean, private val mMessage: String,
    @SerializedName("data") @Expose val logoutData: LogoutDataPojo
) : CommonResponse(mStatus, mMessage) {
    data class LogoutDataPojo(
        @SerializedName("id") @Expose val id: Int,
        @SerializedName("token") @Expose val token: String
    )
}
