package com.e.commerce.ui.common

import com.e.commerce.data.model.auth.FavoritePojo
import com.e.commerce.data.remote.retrofit.ApiControl
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Call

// Created by Hussein_Mohammad on 5/24/2021.
@ViewModelScoped
class AddRemoveFavoriteRepo {
    fun addOrRemoveFavorite(productId: Int): Call<FavoritePojo> {
        return ApiControl.apiService().addOrRemoveFavorite(productId)
    }
}