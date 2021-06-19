package com.e.commerce.ui.fragments.user.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.commerce.data.model.auth.RegisterPojo
import com.e.commerce.data.model.auth.RegisterPojo.RegisterDataPojo
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {
    private val registerRepo = RegisterRepo()


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
}