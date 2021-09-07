package com.e.commerce.ui.fragments.user.login

import com.e.commerce.data.model.auth.LoginPojo
import com.e.commerce.data.model.auth.LoginPojo.LoginDataPojo
import com.e.commerce.data.remote.retrofit.ApiControl
import retrofit2.Call

// Created by Hussein_Mohammad on 6/15/2021.

class LoginRepo {
    fun login(loginPojo: LoginDataPojo): Call<LoginPojo> = ApiControl.apiService().login(loginPojo)
}