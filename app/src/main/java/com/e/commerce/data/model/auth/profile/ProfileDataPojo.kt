package com.e.commerce.data.model.auth.profile

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


// Created by Hussein Mohammed on 9/8/2021.
@Parcelize
data class ProfileDataPojo(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("email") @Expose val email: String,
    @SerializedName("phone") @Expose val phone: String,
    @SerializedName("image") @Expose val image: String,
    @SerializedName("token") @Expose val token: String,
    @SerializedName("points") @Expose val points: Int,
    @SerializedName("credit") @Expose val credit: Double
) : Parcelable
