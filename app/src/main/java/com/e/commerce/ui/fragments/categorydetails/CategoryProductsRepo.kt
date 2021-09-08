package com.e.commerce.ui.fragments.categorydetails

import com.e.commerce.data.model.category.CategoryProductsPojo
import com.e.commerce.data.model.product.ProductsPojo
import com.e.commerce.data.remote.retrofit.ApiControl
import retrofit2.Call

// Created by Hussein_Mohammad on 5/9/2021.

//@ViewModelScoped
class CategoryProductsRepo {

    fun getCategoryProducts(id: Int): Call<CategoryProductsPojo> {
        return ApiControl.apiService().getCategoryProducts(id)
    }

    fun searchProduct(keyword: String): Call<ProductsPojo> {
        return ApiControl.apiService().searchProduct(keyword)
    }
}