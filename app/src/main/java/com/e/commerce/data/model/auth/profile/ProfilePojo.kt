package com.e.commerce.data.model.auth.profile

import android.os.Parcelable
import com.e.commerce.data.model.common.CommonResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// Created by Hussein_Mohammad on 5/14/2021.
@Parcelize
data class ProfilePojo(
    val mStatus: Boolean, val mMessage: String,
    @SerializedName("data") @Expose val profileData: ProfileDataPojo
) : Parcelable, CommonResponse(mStatus, mMessage)