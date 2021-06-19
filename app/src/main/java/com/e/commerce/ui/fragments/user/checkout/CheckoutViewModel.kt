package com.e.commerce.ui.fragments.user.checkout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.commerce.data.model.auth.OrderPojo.AddOrderPojo
import com.e.commerce.data.model.auth.OrderPojo.OrderResponsePojo
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor() : ViewModel() {
    private val checkRepo = CheckoutRepo()

    fun addOrder(order: AddOrderPojo): MutableLiveData<OrderResponsePojo> {
        val orderMutableLD: MutableLiveData<OrderResponsePojo> = MutableLiveData()
        checkRepo.addOrder(order).enqueue(object : Callback<OrderResponsePojo> {
            override fun onResponse(call: Call<OrderResponsePojo>, response: Response<OrderResponsePojo>) {
                if (response.isSuccessful && response.body() != null) {
                    orderMutableLD.value = response.body()
                }
            }

            override fun onFailure(call: Call<OrderResponsePojo>, t: Throwable) {
                Timber.e("AddOrderFailure::${t.localizedMessage}")
            }
        })
        return orderMutableLD
    }
}