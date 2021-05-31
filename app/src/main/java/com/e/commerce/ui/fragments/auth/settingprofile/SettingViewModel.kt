package com.e.commerce.ui.fragments.auth.settingprofile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.commerce.data.model.auth.SettingProfilePojo
import com.e.commerce.data.model.auth.common.SignResponsePojo
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor() : ViewModel() {
    private val settingProfileRepo = SettingProfileRepo()
    var settingProfileMutableData: MutableLiveData<SignResponsePojo> = MutableLiveData()

    fun setSettingProfile(settingPojo: SettingProfilePojo) {
        settingProfileRepo.settingProfile(settingPojo).enqueue(object: Callback<SignResponsePojo>{
            override fun onResponse(call: Call<SignResponsePojo>, response: Response<SignResponsePojo>) {
                if (response.isSuccessful && response.body() != null) {
                    settingProfileMutableData.value = response.body()
                    Timber.d("Setting::Response")
                }
            }

            override fun onFailure(call: Call<SignResponsePojo>, t: Throwable) {
                Timber.e("GetSettingProfileFailure::${t.localizedMessage}")
            }
        })
    }
}