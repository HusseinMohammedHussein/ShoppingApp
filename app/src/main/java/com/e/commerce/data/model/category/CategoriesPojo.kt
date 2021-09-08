package com.e.commerce.data.model.category

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


// Created by Hussein Mohammed on 9/7/2021.
@Parcelize
data class CategoriesPojo(
    @SerializedName("data") @Expose val categoriesData: ArrayList<CategoryItemPojo>
) : Parcelable
