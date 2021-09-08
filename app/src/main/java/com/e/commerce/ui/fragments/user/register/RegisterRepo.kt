package com.e.commerce.ui.fragments.user.register

import com.e.commerce.data.model.auth.register.RegisterDataPojo
import com.e.commerce.data.model.auth.register.RegisterPojo
import com.e.commerce.data.remote.retrofit.ApiControl
import retrofit2.Call

// Created by Hussein_Mohammad on 5/13/2021.

class RegisterRepo {
    fun requestRegister(registerDataPojo: RegisterDataPojo): Call<RegisterPojo> {
        return ApiControl.apiService().register(registerDataPojo)
    }
}