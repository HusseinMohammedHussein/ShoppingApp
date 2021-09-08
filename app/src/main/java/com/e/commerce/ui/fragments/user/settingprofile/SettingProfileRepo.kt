package com.e.commerce.ui.fragments.user.settingprofile

import com.e.commerce.data.model.auth.SettingProfilePojo
import com.e.commerce.data.model.common.SignResponsePojo
import com.e.commerce.data.remote.retrofit.ApiControl
import retrofit2.Call
import timber.log.Timber

// Created by Hussein_Mohammad on 5/31/2021.

class SettingProfileRepo {
    fun settingProfile(settingPojo: SettingProfilePojo): Call<SignResponsePojo> {
        Timber.d("Setting::Repo")
        return ApiControl.apiService().settingProfile(settingPojo)
    }
}