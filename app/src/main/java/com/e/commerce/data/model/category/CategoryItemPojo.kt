package com.e.commerce.data.model.category

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


// Created by Hussein Mohammed on 9/7/2021.
@Parcelize
data class CategoryItemPojo(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("image") @Expose val image: String
) : Parcelable
