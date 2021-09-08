package com.e.commerce.data.model.category

import android.os.Parcelable
import com.e.commerce.data.model.common.CommonResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// Created by Hussein_Mohammad on 5/6/2021.
@Parcelize
data class CategoryPojo(
    private val mStatus: Boolean, private val mMessage: String,
    @SerializedName("data") @Expose val categoriesData: CategoriesPojo
) : CommonResponse(mStatus, mMessage), Parcelable {



}
