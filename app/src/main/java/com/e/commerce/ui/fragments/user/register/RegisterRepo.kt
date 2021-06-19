package com.e.commerce.ui.fragments.user.register

import com.e.commerce.data.model.auth.RegisterPojo
import com.e.commerce.data.model.auth.RegisterPojo.RegisterDataPojo
import com.e.commerce.data.remote.retrofit.ApiControl
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Call

// Created by Hussein_Mohammad on 5/13/2021.

@ViewModelScoped
class RegisterRepo {

    fun requestRegister(registerDataPojo: RegisterDataPojo): Call<RegisterPojo> {
        return ApiControl.apiService().register(registerDataPojo)
    }
}