package com.e.commerce.data.model.category

import com.e.commerce.data.model.product.ProductPojo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Created by Hussein_Mohammad on 5/9/2021.

data class CategoryProductsPojo(
    @SerializedName("data") @Expose val categoryData: CategoryProductsDataPojo
) {
    data class CategoryProductsDataPojo(
        @SerializedName("data") @Expose val products: ArrayList<ProductPojo>
    )
}