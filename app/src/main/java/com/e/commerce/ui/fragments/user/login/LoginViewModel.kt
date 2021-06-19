package com.e.commerce.ui.fragments.user.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.commerce.data.model.auth.LoginPojo
import com.e.commerce.data.model.auth.LoginPojo.LoginDataPojo
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val loginRepo = LoginRepo()

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
}