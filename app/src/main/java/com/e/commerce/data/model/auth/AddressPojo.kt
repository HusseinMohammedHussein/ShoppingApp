package com.e.commerce.data.model.auth

import android.os.Parcelable
import com.e.commerce.data.model.auth.common.CommonResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

// Created by Hussein_Mohammad on 5/30/2021.

@Parcelize
data class AddressPojo(
    val mStatus: Boolean, val mMessage: String,
    @SerializedName("data") @Expose val data: AddressDataPojo,
) : CommonResponse(mStatus, mMessage), Parcelable {

    @Parcelize
    data class AddressDataPojo(
        @SerializedName("total") @Expose val total: Int,
        @SerializedName("data") @Expose val data: ArrayList<AddressObjectPojo>
    ) : Parcelable {
        @Parcelize
        data class AddressObjectPojo(
            @SerializedName("id") @Expose val id: Int,
            @SerializedName("name") @Expose val name: String,
            @SerializedName("city") @Expose val city: String,
            @SerializedName("notes") @Expose val notes: String,
            @SerializedName("region") @Expose val region: String,
            @SerializedName("details") @Expose val details: String,
            @SerializedName("latitude") @Expose val latitude: Double,
            @SerializedName("longitude") @Expose val longitude: Double
        ) : Parcelable
    }

    @Parcelize
    data class AddAddressPojo(
        @SerializedName("name") @Expose val name: String,
        @SerializedName("city") @Expose val city: String,
        @SerializedName("region") @Expose val region: String,
        @SerializedName("details") @Expose val details: String,
        @SerializedName("notes") @Expose val notes: String,
        @SerializedName("latitude") @Expose val latitude: Long,
        @SerializedName("longitude") @Expose val longitude: Long
    ): Parcelable
}