package com.e.commerce.ui.fragments.productdetails

import com.e.commerce.data.model.product.ProductDetailsPojo
import com.e.commerce.data.model.product.ProductsPojo
import com.e.commerce.data.model.auth.favorite.FavoritePojo
import com.e.commerce.data.model.auth.bag.BagItemAddRemovePojo
import com.e.commerce.data.remote.retrofit.ApiControl
import retrofit2.Call

// Created by Hussein_Mohammad on 5/10/2021.

class ProductDetailsRepo {

    fun getProductDetails(id: Int): Call<ProductDetailsPojo> {
        return ApiControl.apiService().getProductDetails(id)
    }

    fun getProducts(): Call<ProductsPojo> {
        return ApiControl.apiService().getProducts()
    }

    fun addOrRemoveFromBag(productId: Int): Call<BagItemAddRemovePojo> {
        return  ApiControl.apiService().addOrRemoveBag(productId)
    }

    fun addOrRemoveFromFavorites(productId: Int): Call<FavoritePojo>{
        return ApiControl.apiService().addOrRemoveFavorite(productId)
    }
}