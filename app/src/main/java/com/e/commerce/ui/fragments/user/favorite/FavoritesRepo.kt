package com.e.commerce.ui.fragments.user.favorite

import com.e.commerce.data.model.auth.favorite.FavoritePojo
import com.e.commerce.data.remote.retrofit.ApiControl
import retrofit2.Call

// Created by Hussein_Mohammad on 5/16/2021.
class FavoritesRepo {

    fun getFavorites(): Call<FavoritePojo> {
        return ApiControl.apiService().getFavorites()
    }
}