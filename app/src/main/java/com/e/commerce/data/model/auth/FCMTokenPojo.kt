package com.e.commerce.data.model.auth

import com.e.commerce.data.model.auth.common.CommonResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Created by Hussein_Mohammad on 6/25/2021.
data class FCMTokenPojo(
    private val mStatus: Boolean, private val mMessage: String,
    @SerializedName("") @Expose val data: FCMResponsePojo
) : CommonResponse(mStatus, mMessage) {
    data class FCMResponsePojo(
        @SerializedName("user_id") @Expose val user_id: Int,
        @SerializedName("id") @Expose val id: Int,
        @SerializedName("token") @Expose val token: String,
        @SerializedName("updated_at") @Expose val updated_at: String,
        @SerializedName("created_at") @Expose val created_at: String
    )
}
