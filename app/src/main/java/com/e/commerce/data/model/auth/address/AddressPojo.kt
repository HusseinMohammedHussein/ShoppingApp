package com.e.commerce.data.model.auth.address

import android.os.Parcelable
import com.e.commerce.data.model.common.CommonResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// Created by Hussein_Mohammad on 5/30/2021.

@Parcelize
data class AddressPojo(
    val mStatus: Boolean, val mMessage: String,
    @SerializedName("data") @Expose val addressData: AddressesDataPojo,
) : CommonResponse(mStatus, mMessage), Parcelable