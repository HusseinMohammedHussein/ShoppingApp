package com.e.commerce.ui.fragments.categories

import com.e.commerce.data.model.ShopCategoryPojo
import com.e.commerce.data.model.home.CategoryPojo
import com.e.commerce.data.remote.retrofit.ApiControl
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Call

// Created by Hussein_Mohammad on 5/8/2021.

@ViewModelScoped
class ShopCategoryRepo {
    fun getShopCategory(): Call<CategoryPojo> {
        return ApiControl.apiService().getShopCategory()
    }
}