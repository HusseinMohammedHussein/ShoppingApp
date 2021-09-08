package com.e.commerce.interfaces

import com.e.commerce.data.model.auth.address.AddressesDataPojo

interface AddressInterface {
    fun onAddressItemClick(address: AddressesDataPojo.AddressDataPojo)
}