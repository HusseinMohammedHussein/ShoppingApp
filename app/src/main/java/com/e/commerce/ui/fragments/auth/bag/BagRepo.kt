package com.e.commerce.ui.fragments.auth.bag

import com.e.commerce.data.model.auth.bag.BagsPojo
import com.e.commerce.data.model.auth.bag.UpdateBagResponsePojo
import com.e.commerce.data.remote.retrofit.ApiControl
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Call

// Created by Hussein_Mohammad on 5/19/2021.
@ViewModelScoped
class BagRepo {

    fun getBag(): Call<BagsPojo> {
        return ApiControl.apiService().getBag()
    }

    fun updateBag(bagProductId: Int, hashMap: HashMap<String, Int>): Call<UpdateBagResponsePojo> {
        return ApiControl.apiService().updateQuantityBag(bagProductId, hashMap)
    }
}