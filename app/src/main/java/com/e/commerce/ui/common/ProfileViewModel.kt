package com.e.commerce.ui.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.commerce.data.model.ProfilePojo
import com.e.commerce.data.model.auth.AddressPojo
import com.e.commerce.data.model.auth.LogoutPojo
import com.e.commerce.data.model.auth.OrderPojo
import com.e.commerce.ui.fragments.user.profile.ProfileRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class ProfileViewModel : ViewModel() {
    private val profileRepo = ProfileRepo()
    var profileMutableLD: MutableLiveData<ProfilePojo> = MutableLiveData()
    var totalOrdersMutableLD: MutableLiveData<OrderPojo> = MutableLiveData()
    var addressMutableLD: MutableLiveData<AddressPojo> = MutableLiveData()

    fun getProfile() {
        profileRepo.getProfile().enqueue(object : Callback<ProfilePojo> {
            override fun onResponse(call: Call<ProfilePojo>, response: Response<ProfilePojo>) {
                if (response.isSuccessful && response.body() != null) {
                    profileMutableLD.value = response.body()
                }
            }

            override fun onFailure(call: Call<ProfilePojo>, t: Throwable) {
                Timber.e("getProfileFailure::${t.localizedMessage}")
            }

        })
    }

    fun getTotalOrders() {
        profileRepo.getTotalOrders().enqueue(object : Callback<OrderPojo> {
            override fun onResponse(call: Call<OrderPojo>, response: Response<OrderPojo>) {
                if (response.isSuccessful && response.body() != null) {
                    totalOrdersMutableLD.value = response.body()
                }
            }

            override fun onFailure(call: Call<OrderPojo>, t: Throwable) {
                Timber.e("getOrdersFailure::${t.localizedMessage}")
            }
        })
    }

    fun getAddress() {
        profileRepo.getAddress().enqueue(object : Callback<AddressPojo> {
            override fun onResponse(call: Call<AddressPojo>, response: Response<AddressPojo>) {
                if (response.isSuccessful && response.body() != null) {
                    addressMutableLD.value = response.body()
                }
            }

            override fun onFailure(call: Call<AddressPojo>, t: Throwable) {
                Timber.e("getAddressFailure::${t.localizedMessage}")
            }
        })
    }

    fun logout(fcmToken: String): MutableLiveData<LogoutPojo> {
        val logoutMutableLD: MutableLiveData<LogoutPojo> = MutableLiveData()
        profileRepo.logout(fcmToken).enqueue(object : Callback<LogoutPojo> {
            override fun onResponse(call: Call<LogoutPojo>, response: Response<LogoutPojo>) {
                if (response.isSuccessful && response.body() != null) {
                    logoutMutableLD.value = response.body()
                }
            }

            override fun onFailure(call: Call<LogoutPojo>, t: Throwable) {
                Timber.e("LogoutFailure::${t.localizedMessage}")
            }
        })
        return logoutMutableLD
    }
}