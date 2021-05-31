package com.e.commerce.data.model.auth.bag

import android.os.Parcelable
import com.e.commerce.data.model.auth.common.BagItemResponsePojo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// Created by Hussein_Mohammad on 5/16/2021.
@Parcelize
data class BagPojo(
    @SerializedName("status") @Expose val status: Boolean,
    @SerializedName("message") @Expose var message: String,
    @SerializedName("data") @Expose val addedBagResponseData: BagItemResponsePojo
) : Parcelable
