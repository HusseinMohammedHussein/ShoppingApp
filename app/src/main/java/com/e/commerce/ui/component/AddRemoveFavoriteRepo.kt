package com.e.commerce.ui.component

import com.e.commerce.data.model.auth.favorite.FavoritePojo
import com.e.commerce.data.remote.retrofit.ApiControl
import retrofit2.Call

// Created by Hussein_Mohammad on 5/24/2021.
class AddRemoveFavoriteRepo {
    fun addOrRemoveFavorite(productId: Int): Call<FavoritePojo> {
        return ApiControl.apiService().addOrRemoveFavorite(productId)
    }
}