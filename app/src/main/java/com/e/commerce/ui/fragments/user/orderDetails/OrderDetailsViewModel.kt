package com.e.commerce.ui.fragments.user.orderDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.commerce.data.model.auth.OrderDetailsPojo
import com.e.commerce.data.model.auth.PromocodePojo
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor() : ViewModel() {
    private val orderDetailsRepo = OrderDetailsRepo()

    fun getOrderDetails(orderId: Int): MutableLiveData<OrderDetailsPojo> {
        val orderDetailsMutableLD: MutableLiveData<OrderDetailsPojo> = MutableLiveData()
        orderDetailsRepo.getOrderDetails(orderId).enqueue(object : Callback<OrderDetailsPojo> {
            override fun onResponse(call: Call<OrderDetailsPojo>, response: Response<OrderDetailsPojo>) {
                if (response.isSuccessful && response.body() != null) {
                    orderDetailsMutableLD.value = response.body()
                }
            }

            override fun onFailure(call: Call<OrderDetailsPojo>, t: Throwable) {
                Timber.e("OrderDetailsFailure::${t.localizedMessage}")
            }
        })
        return orderDetailsMutableLD
    }

    fun getPromocodePercentage(code: String): MutableLiveData<PromocodePojo> {
        val promocodePercentage: MutableLiveData<PromocodePojo> = MutableLiveData()
        orderDetailsRepo.getPromocodePercentage(code).enqueue(object : Callback<PromocodePojo>{
            override fun onResponse(call: Call<PromocodePojo>, response: Response<PromocodePojo>) {
                if (response.isSuccessful && response.body() != null) {
                    promocodePercentage.value = response.body()
                }
            }

            override fun onFailure(call: Call<PromocodePojo>, t: Throwable) {
                Timber.e("getPromocodePercentageFailure::${t.localizedMessage}")
            }
        })
        return promocodePercentage
    }
}