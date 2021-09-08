package com.e.commerce.ui.fragments.user.bag

import com.e.commerce.data.model.auth.PromocodePojo
import com.e.commerce.data.model.auth.bag.BagItemAddRemovePojo
import com.e.commerce.data.model.auth.bag.BagProductsPojo
import com.e.commerce.data.model.auth.bag.BagUpdatePojo
import com.e.commerce.data.remote.retrofit.ApiControl
import retrofit2.Call

// Created by Hussein_Mohammad on 5/19/2021.
class BagRepo {

    fun getBag(): Call<BagProductsPojo> {
        return ApiControl.apiService().getBagProducts()
    }

    fun updateQuantityBag(bagProductId: Int, hashMap: HashMap<String, Int>): Call<BagUpdatePojo> {
        return ApiControl.apiService().updateQuantityBag(bagProductId, hashMap)
    }

    fun removeBagProduct(productId: Int): Call<BagItemAddRemovePojo> {
        return ApiControl.apiService().addOrRemoveBag(productId)
    }

    fun checkPromoCode(code: String): Call<PromocodePojo> {
        return  ApiControl.apiService().checkPromoCode(code)
    }
}