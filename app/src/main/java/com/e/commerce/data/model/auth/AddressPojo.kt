package com.e.commerce.data.model.auth

import com.e.commerce.data.model.auth.common.CommonResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Created by Hussein_Mohammad on 5/30/2021.

data class AddressPojo(
    @SerializedName("data") @Expose val data: AddressDataPojo,
    val mStatus: Boolean,
    val mMessage: String
) : CommonResponse(mStatus, mMessage) {

    data class AddressDataPojo(
        @SerializedName("total") @Expose val total: Int,
        @SerializedName("data") @Expose val data: MutableList<AddressesPojo>
    ) {
        data class AddressesPojo(
            @SerializedName("id") @Expose val id: Int,
            @SerializedName("name") @Expose val name: String,
            @SerializedName("city") @Expose val city: String,
            @SerializedName("region") @Expose val region: String,
            @SerializedName("notes") @Expose val notes: String,
            @SerializedName("latitude") @Expose val latitude: Double,
            @SerializedName("longitude") @Expose val longitude: Double
        )
    }
}
