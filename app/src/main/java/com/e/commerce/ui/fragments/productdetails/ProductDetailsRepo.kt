package com.e.commerce.ui.fragments.productdetails

import com.e.commerce.data.model.ProductDetailsPojo
import com.e.commerce.data.model.ProductsPojo
import com.e.commerce.data.model.auth.bag.BagPojo
import com.e.commerce.data.model.auth.FavoritePojo
import com.e.commerce.data.remote.retrofit.ApiControl
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Call

// Created by Hussein_Mohammad on 5/10/2021.

@ViewModelScoped
class ProductDetailsRepo {

    fun getProductDetails(id: Int): Call<ProductDetailsPojo> {
        return ApiControl.apiService().getProductDetails(id)
    }

    fun getProducts(): Call<ProductsPojo> {
        return ApiControl.apiService().getProducts()
    }

    fun addToCart(productId: Int): Call<BagPojo> {
        return  ApiControl.apiService().addOrRemoveBag(productId)
    }

    fun addToFavorites(productId: Int): Call<FavoritePojo>{
        return ApiControl.apiService().addOrRemoveFavorite(productId)
    }
}