package com.e.commerce.ui.fragments.auth.register

import com.e.commerce.data.model.RegisterPojo
import com.e.commerce.data.model.RegisterPojo.RequestRegisterPojo
import com.e.commerce.data.remote.retrofit.ApiControl
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Call

// Created by Hussein_Mohammad on 5/13/2021.

@ViewModelScoped
class RegisterRepo {

    fun requestRegister(requestRegisterPojo: RequestRegisterPojo): Call<RegisterPojo> {
        return ApiControl.apiService().requestRegister(requestRegisterPojo)
    }
}