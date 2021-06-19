package com.e.commerce.data.model.auth

import android.os.Parcelable
import com.e.commerce.data.model.auth.common.CommonResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// Created by Hussein_Mohammad on 6/7/2021.

@Parcelize
data class PromocodePojo(
    private val mStatus: Boolean, private val mMessage: String,
    @SerializedName("data") @Expose val data: PromocodeDataPojo
) : CommonResponse(mStatus, mMessage), Parcelable {
    @Parcelize
    data class PromocodeDataPojo(
        @SerializedName("id") @Expose val id: Int,
        @SerializedName("code") @Expose val code: String,
        @SerializedName("value") @Expose val value: Int,
        @SerializedName("percentage") @Expose val percentage: Int,
        @SerializedName("start_date") @Expose val start_date: String,
        @SerializedName("end_date") @Expose val end_date: String,
        @SerializedName("usage_per_user") @Expose val usage_per_user: Int
    ) : Parcelable
}
