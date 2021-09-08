package com.e.commerce.interfaces

import com.e.commerce.data.model.auth.bag.BagItemPojo

interface AddRemoveBagItemInterface {
    fun onAddRemoveFavoriteBagItem(bagPojo: BagItemPojo)
    fun onAddRemoveBagItem(bagPojo: BagItemPojo)
}