package com.e.commerce.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// Created by Hussein_Mohammad on 5/14/2021.
@Parcelize
data class ProfilePojo(
    @SerializedName("status") @Expose val status: Boolean,
    @SerializedName("message") @Expose val message: String,
    @SerializedName("data") @Expose val data: ProfileResponsePojo
) : Parcelable {
    @Parcelize
    data class ProfileResponsePojo(
        @SerializedName("id") @Expose val id: Int,
        @SerializedName("name") @Expose val name: String,
        @SerializedName("email") @Expose val email: String,
        @SerializedName("phone") @Expose val phone: String,
        @SerializedName("image") @Expose val image: String,
        @SerializedName("token") @Expose val token: String,
        @SerializedName("points") @Expose val points: Int,
        @SerializedName("credit") @Expose val credit: Double
    ) : Parcelable
}