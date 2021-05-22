package com.e.commerce.ui.fragments.categorydetails

import com.e.commerce.data.model.CategoryDetailsPojo
import com.e.commerce.data.remote.retrofit.ApiControl
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Call

// Created by Hussein_Mohammad on 5/9/2021.

@ViewModelScoped
class CategoryDetailsRepo {

    fun getCategoryDetails(id: Int): Call<CategoryDetailsPojo> {
        return ApiControl.apiService().getCategoryDetails(id)
    }
}