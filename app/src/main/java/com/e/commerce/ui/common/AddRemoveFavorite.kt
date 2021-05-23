package com.e.commerce.ui.common

import com.e.commerce.data.model.auth.FavoritesPojo
import com.e.commerce.data.remote.retrofit.ApiControl
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Call

// Created by Hussein_Mohammad on 5/24/2021.
@ViewModelScoped
class AddRemoveFavorite {
    fun addOrRemoveFavorite(productId: Int): Call<FavoritesPojo> {
        return ApiControl.apiService().addOrRemoveFavorite(productId)
    }
}