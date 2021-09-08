package com.e.commerce.ui.fragments.user.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.commerce.data.model.auth.FCMTokenPojo
import com.e.commerce.data.model.auth.login.LoginDataPojo
import com.e.commerce.data.model.auth.login.LoginPojo
import com.e.commerce.ui.component.FCMTokenRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class LoginViewModel : ViewModel() {
    private val loginRepo = LoginRepo()
    private val fcmTokenRepo = FCMTokenRepo()

    fun login(loginPojo: LoginDataPojo): MutableLiveData<LoginPojo> {
        val loginMutableLD: MutableLiveData<LoginPojo> = MutableLiveData()
        loginRepo.login(loginPojo).enqueue(object : Callback<LoginPojo> {
            override fun onResponse(call: Call<LoginPojo>, response: Response<LoginPojo>) {
                if (response.isSuccessful && response.body() != null) {
                    loginMutableLD.value = response.body()
                }
            }

            override fun onFailure(call: Call<LoginPojo>, t: Throwable) {
                Timber.e("LoginFailure::${t.localizedMessage}")
            }
        })
        return loginMutableLD
    }

    fun setFCMToken(token: String): MutableLiveData<FCMTokenPojo> {
        val fcmResponseMutableLD: MutableLiveData<FCMTokenPojo> = MutableLiveData()
        fcmTokenRepo.setFCMToken(token).enqueue(object : Callback<FCMTokenPojo> {
            override fun onResponse(call: Call<FCMTokenPojo>, response: Response<FCMTokenPojo>) {
                if (response.isSuccessful && response.body() != null) {
                    fcmResponseMutableLD.value = response.body()
                }
            }

            override fun onFailure(call: Call<FCMTokenPojo>, t: Throwable) {
                Timber.e("SetFCMTokenFailure::${t.localizedMessage}")
            }
        })
        return fcmResponseMutableLD
    }
}