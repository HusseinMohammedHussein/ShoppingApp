package com.e.commerce.ui.fragments.auth.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.commerce.data.model.ProfilePojo
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {
    private val profileRepo = ProfileRepo()
    var profileMutable: MutableLiveData<ProfilePojo> = MutableLiveData()

    fun getProfile() {
        profileRepo.getProfile().enqueue(object : Callback<ProfilePojo> {
            override fun onResponse(call: Call<ProfilePojo>, response: Response<ProfilePojo>) {
                if (response.isSuccessful && response.body() != null) {
                    profileMutable.value = response.body()
                }
            }

            override fun onFailure(call: Call<ProfilePojo>, t: Throwable) {
                Timber.d("getProfileFailure::${t.localizedMessage}")
            }

        })
    }
}