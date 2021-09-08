package com.e.commerce.ui.fragments.home

import com.e.commerce.data.model.auth.favorite.FavoritePojo
import com.e.commerce.data.model.category.CategoryPojo
import com.e.commerce.data.model.home.HomePojo
import com.e.commerce.data.remote.retrofit.ApiControl
import retrofit2.Call

// Created by Hussein_Mohammad on 5/2/2021.

class HomeRepo {

    fun getHome(): Call<HomePojo> {
        return ApiControl.apiService().getHomeData()
    }

    fun getCategories(): Call<CategoryPojo> {
        return ApiControl.apiService().getCategories()
    }

    fun addFavorite(productId: Int): Call<FavoritePojo> {
        return ApiControl.apiService().addOrRemoveFavorite(productId)
    }
}