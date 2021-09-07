package com.e.commerce.ui.fragments.user.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.commerce.data.model.auth.FCMTokenPojo
import com.e.commerce.data.model.auth.RegisterPojo
import com.e.commerce.data.model.auth.RegisterPojo.RegisterDataPojo
import com.e.commerce.ui.common.FCMTokenRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class RegisterViewModel : ViewModel() {
    private val registerRepo = RegisterRepo()
    private val fcmTokenRepo = FCMTokenRepo()


    fun requestRegister(registerDataPojo: RegisterDataPojo): MutableLiveData<RegisterPojo> {
        val responseRegisterMutable: MutableLiveData<RegisterPojo> = MutableLiveData()
        registerRepo.requestRegister(registerDataPojo).enqueue(object : Callback<RegisterPojo> {
            override fun onResponse(call: Call<RegisterPojo>, response: Response<RegisterPojo>) {
                if (response.isSuccessful && response.body() != null) {
                    responseRegisterMutable.value = response.body()
                }
            }

            override fun onFailure(call: Call<RegisterPojo>, t: Throwable) {
                Timber.e("responseRegisterFailure::${t.localizedMessage}")
            }

        })
        return responseRegisterMutable
    }

    fun setFCMToken(fcmToken: String): MutableLiveData<FCMTokenPojo> {
        val fcmTokenMutableLD: MutableLiveData<FCMTokenPojo> = MutableLiveData()
        fcmTokenRepo.setFCMToken(fcmToken).enqueue(object : Callback<FCMTokenPojo> {
            override fun onResponse(call: Call<FCMTokenPojo>, response: Response<FCMTokenPojo>) {
                if (response.isSuccessful && response.body() != null) {
                    fcmTokenMutableLD.value = response.body()
                }
            }

            override fun onFailure(call: Call<FCMTokenPojo>, t: Throwable) {
                Timber.e("FCMTokenFailure::${t.localizedMessage}")
            }
        })
        return fcmTokenMutableLD
    }
}