package com.e.commerce.ui.fragments.auth.favorite

import com.e.commerce.data.model.auth.FavoritePojo
import com.e.commerce.data.remote.retrofit.ApiControl
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Call

// Created by Hussein_Mohammad on 5/16/2021.
@ViewModelScoped
class FavoritesRepo {

    fun getFavorites(): Call<FavoritePojo> {
        return ApiControl.apiService().getFavorites()
    }
}