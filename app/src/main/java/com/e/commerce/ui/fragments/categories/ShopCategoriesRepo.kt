package com.e.commerce.ui.fragments.categories

import com.e.commerce.data.model.category.CategoryPojo
import com.e.commerce.data.remote.retrofit.ApiControl
import retrofit2.Call

// Created by Hussein_Mohammad on 5/8/2021.

//@ViewModelScoped
class ShopCategoriesRepo {
    fun getShopCategories(): Call<CategoryPojo> {
        return ApiControl.apiService().getCategories()
    }
}