package com.e.commerce.data.model.home

import android.os.Parcelable
import com.e.commerce.data.model.auth.common.CommonResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// Created by Hussein_Mohammad on 5/6/2021.
@Parcelize
data class CategoryPojo(
    private val mStatus: Boolean, private val mMessage: String,
    @SerializedName("data") @Expose val data: Data
) : CommonResponse(mStatus, mMessage), Parcelable {
    @Parcelize
    data class Data(
        @SerializedName("data") @Expose val categories: ArrayList<CategoriesPojo>,
    ) : Parcelable {
        @Parcelize
        data class CategoriesPojo(
            @SerializedName("id") @Expose val id: Int,
            @SerializedName("name") @Expose val name: String,
            @SerializedName("image") @Expose val image: String
        ) : Parcelable
    }
}
