package com.e.commerce.ui.fragments.user.address.addAddress

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.commerce.data.model.auth.address.AddAddressPojo
import com.e.commerce.data.model.auth.address.AddressPojo
import com.e.commerce.data.model.auth.address.AddressesDataPojo.AddressDataPojo
import com.e.commerce.ui.fragments.user.address.AddressRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class AddAddressViewModel : ViewModel() {
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

    fun updateAddress(addressId: Int, updateAddress: AddressDataPojo): MutableLiveData<AddressPojo> {
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