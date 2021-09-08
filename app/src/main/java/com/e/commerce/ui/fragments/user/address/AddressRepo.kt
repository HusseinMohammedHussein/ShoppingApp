package com.e.commerce.ui.fragments.user.address

import com.e.commerce.data.model.auth.address.AddAddressPojo
import com.e.commerce.data.model.auth.address.AddressPojo
import com.e.commerce.data.model.auth.address.AddressesDataPojo.AddressDataPojo
import com.e.commerce.data.remote.retrofit.ApiControl
import retrofit2.Call

// Created by Hussein_Mohammad on 6/8/2021.

//@ViewModelScoped
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

    fun updateAddress(addressId: Int, updateAddress: AddressDataPojo): Call<AddressPojo> {
        return ApiControl.apiService().updateAddresses(addressId, updateAddress)
    }
}