package com.e.commerce.ui.fragments.user.address.addresses

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.commerce.data.model.auth.address.AddressPojo
import com.e.commerce.ui.fragments.user.address.AddressRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class AddressViewModel : ViewModel() {

    private val addressRepo = AddressRepo()
    val addressMutableLD: MutableLiveData<AddressPojo> = MutableLiveData()

    fun getAddress() {
        addressRepo.getAddresses().enqueue(object : Callback<AddressPojo> {
            override fun onResponse(call: Call<AddressPojo>, response: Response<AddressPojo>) {
                if (response.isSuccessful && response.body() != null) {
                    addressMutableLD.value = response.body()
                }
            }

            override fun onFailure(call: Call<AddressPojo>, t: Throwable) {
                Timber.e("GetAddressFailure::${t.localizedMessage}")
            }
        })
    }

    fun deleteAddress(addressId: Int): MutableLiveData<AddressPojo> {
        val deleteAddressMutableLD: MutableLiveData<AddressPojo> = MutableLiveData()
        addressRepo.deleteAddress(addressId).enqueue(object : Callback<AddressPojo> {
            override fun onResponse(call: Call<AddressPojo>, response: Response<AddressPojo>) {
                if (response.isSuccessful && response.body() != null) {
                    deleteAddressMutableLD.value = response.body()
                }
            }

            override fun onFailure(call: Call<AddressPojo>, t: Throwable) {
                Timber.e("DeleteAddressFailure::${t.localizedMessage}")
            }
        })
        return deleteAddressMutableLD
    }
}