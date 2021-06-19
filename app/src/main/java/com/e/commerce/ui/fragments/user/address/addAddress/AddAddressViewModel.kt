package com.e.commerce.ui.fragments.user.address.addAddress

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.commerce.data.model.auth.AddressPojo
import com.e.commerce.data.model.auth.AddressPojo.AddAddressPojo
import com.e.commerce.data.model.auth.AddressPojo.AddressDataPojo.AddressObjectPojo
import com.e.commerce.ui.fragments.user.address.AddressRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddAddressViewModel @Inject constructor() : ViewModel() {
    private val addressRepo = AddressRepo()

    fun addAddress(addAddress: AddAddressPojo): MutableLiveData<AddressPojo> {
        val addAddressMutableLD: MutableLiveData<AddressPojo> = MutableLiveData()

        addressRepo.addAddress(addAddress).enqueue(object : Callback<AddressPojo> {
            override fun onResponse(call: Call<AddressPojo>, response: Response<AddressPojo>) {
                if (response.isSuccessful && response.body() != null) {
                    addAddressMutableLD.value = response.body()
                }
            }

            override fun onFailure(call: Call<AddressPojo>, t: Throwable) {
                Timber.e("AddAddressFailure::${t.localizedMessage}")
            }
        })
        return addAddressMutableLD
    }

    fun updateAddress(addressId: Int, updateAddress: AddressObjectPojo): MutableLiveData<AddressPojo> {
        val updateAddressMutableLD: MutableLiveData<AddressPojo> = MutableLiveData()
        addressRepo.updateAddress(addressId, updateAddress).enqueue(object : Callback<AddressPojo> {
            override fun onResponse(call: Call<AddressPojo>, response: Response<AddressPojo>) {
                if (response.isSuccessful && response.body() != null) {
                    updateAddressMutableLD.value = response.body()
                }
            }

            override fun onFailure(call: Call<AddressPojo>, t: Throwable) {
                Timber.e("UpdateAddressFailure::${t.localizedMessage}")
            }
        })
        return updateAddressMutableLD
    }
}