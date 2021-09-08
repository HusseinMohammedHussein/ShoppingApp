package com.e.commerce.data.model.auth.address

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*


// Created by Hussein Mohammed on 9/7/2021.

@Parcelize
data class AddressesDataPojo(
    @SerializedName("total") @Expose val total: Int,
    @SerializedName("data") @Expose val addressesData: ArrayList<AddressDataPojo>
) : Parcelable {
    @Parcelize
    data class AddressDataPojo(
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
