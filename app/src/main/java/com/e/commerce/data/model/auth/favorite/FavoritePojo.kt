package com.e.commerce.data.model.auth.favorite

import android.os.Parcelable
import com.e.commerce.data.model.common.CommonResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// Created by Hussein_Mohammad on 5/16/2021.
@Parcelize
data class FavoritePojo(
    val mStatus: Boolean, val mMessage: String,
    @SerializedName("data") @Expose val favoriteDataPojo: FavoritesDataPojo
) : Parcelable, CommonResponse(mStatus, mMessage)
