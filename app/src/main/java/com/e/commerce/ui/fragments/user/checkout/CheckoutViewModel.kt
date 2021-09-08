package com.e.commerce.ui.fragments.user.checkout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.commerce.data.model.auth.order.AddOrderPojo
import com.e.commerce.data.model.auth.order.OrderAddedResponsePojo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class CheckoutViewModel : ViewModel() {
    private val checkRepo = CheckoutRepo()

    fun addOrder(order: AddOrderPojo): MutableLiveData<OrderAddedResponsePojo> {
        val orderAddedMutableLD: MutableLiveData<OrderAddedResponsePojo> = MutableLiveData()
        checkRepo.addOrder(order).enqueue(object : Callback<OrderAddedResponsePojo> {
            override fun onResponse(call: Call<OrderAddedResponsePojo>, addedResponse: Response<OrderAddedResponsePojo>) {
                if (addedResponse.isSuccessful && addedResponse.body() != null) {
                    orderAddedMutableLD.value = addedResponse.body()
                }
            }

            override fun onFailure(call: Call<OrderAddedResponsePojo>, t: Throwable) {
                Timber.e("AddOrderFailure::${t.localizedMessage}")
            }
        })
        return orderAddedMutableLD
    }
}