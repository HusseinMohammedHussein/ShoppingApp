package com.e.commerce.ui.fragments.auth.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.commerce.data.model.ProfilePojo
import com.e.commerce.data.model.auth.AddressPojo
import com.e.commerce.data.model.auth.OrderPojo
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
    var ordersMutable: MutableLiveData<OrderPojo> = MutableLiveData()
    var addressMutable: MutableLiveData<AddressPojo> = MutableLiveData()

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

    fun getOrders() {
        profileRepo.getOrders().enqueue(object : Callback<OrderPojo> {
            override fun onResponse(call: Call<OrderPojo>, response: Response<OrderPojo>) {
                if (response.isSuccessful && response.body() != null) {
                    ordersMutable.value = response.body()
                }
            }

            override fun onFailure(call: Call<OrderPojo>, t: Throwable) {
                Timber.e("getOrdersFailure::${t.localizedMessage}")
            }
        })
    }

    fun getAddress() {
        profileRepo.getAddress().enqueue(object: Callback<AddressPojo>{
            override fun onResponse(call: Call<AddressPojo>, response: Response<AddressPojo>) {
                if (response.isSuccessful && response.body() != null) {
                    addressMutable.value = response.body()
                }
            }

            override fun onFailure(call: Call<AddressPojo>, t: Throwable) {
                Timber.e("getAddressFailure::${t.localizedMessage}")
            }
        })
    }
}