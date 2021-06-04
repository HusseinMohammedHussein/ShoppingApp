package com.e.commerce.data.model.auth.common

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Created by Hussein_Mohammad on 5/30/2021.
open class CommonResponse constructor(
    @SerializedName("status") @Expose var status: Boolean,
    @SerializedName("message") @Expose var message: String
)
