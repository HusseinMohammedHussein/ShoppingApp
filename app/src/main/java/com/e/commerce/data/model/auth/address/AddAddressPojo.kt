package com.e.commerce.data.model.auth.address

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// Created by Hussein Mohammed on 9/7/2021.

@Parcelize
data class AddAddressPojo(
    @SerializedName("name") @Expose val name: String,
    @SerializedName("city") @Expose val city: String,
    @SerializedName("notes") @Expose val notes: String,
    @SerializedName("region") @Expose val region: String,
    @SerializedName("details") @Expose val details: String,
    @SerializedName("latitude") @Expose val latitude: Long,
    @SerializedName("longitude") @Expose val longitude: Long
) : Parcelable
