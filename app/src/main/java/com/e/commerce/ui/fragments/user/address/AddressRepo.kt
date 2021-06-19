package com.e.commerce.ui.fragments.user.address

import com.e.commerce.data.model.auth.AddressPojo
import com.e.commerce.data.model.auth.AddressPojo.AddAddressPojo
import com.e.commerce.data.model.auth.AddressPojo.AddressDataPojo.AddressObjectPojo
import com.e.commerce.data.remote.retrofit.ApiControl
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Call

// Created by Hussein_Mohammad on 6/8/2021.

@ViewModelScoped
class AddressRepo {

    fun getAddresses(): Call<AddressPojo> {
        return ApiControl.apiService().getAddresses()
    }

    fun addAddress(addAddress: AddAddressPojo): Call<AddressPojo> {
        return ApiControl.apiService().addAddresses(addAddress)
    }

    fun deleteAddress(addressId: Int): Call<AddressPojo> {
        return ApiControl.apiService().deleteAddresses(addressId)
    }

    fun updateAddress(addressId: Int, updateAddress: AddressObjectPojo): Call<AddressPojo> {
        return ApiControl.apiService().updateAddresses(addressId, updateAddress)
    }
}