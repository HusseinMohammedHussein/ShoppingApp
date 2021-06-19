package com.e.commerce.ui.fragments.user.bag

import com.e.commerce.data.model.auth.PromocodePojo
import com.e.commerce.data.model.auth.bag.BagPojo
import com.e.commerce.data.model.auth.bag.BagResponsePojo
import com.e.commerce.data.model.auth.bag.BagUpdateResponsePojo
import com.e.commerce.data.remote.retrofit.ApiControl
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Call

// Created by Hussein_Mohammad on 5/19/2021.
@ViewModelScoped
class BagRepo {

    fun getBag(): Call<BagResponsePojo> {
        return ApiControl.apiService().getBag()
    }

    fun updateQuantityBag(bagProductId: Int, hashMap: HashMap<String, Int>): Call<BagUpdateResponsePojo> {
        return ApiControl.apiService().updateQuantityBag(bagProductId, hashMap)
    }

    fun removeBagProduct(productId: Int): Call<BagPojo> {
        return ApiControl.apiService().addOrRemoveBag(productId)
    }

    fun checkPromoCode(code: String): Call<PromocodePojo> {
        return  ApiControl.apiService().checkPromoCode(code)
    }
}