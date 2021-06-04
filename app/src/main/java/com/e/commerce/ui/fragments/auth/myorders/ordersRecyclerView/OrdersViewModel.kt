package com.e.commerce.ui.fragments.auth.myorders.ordersRecyclerView

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.commerce.data.model.auth.OrderPojo
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor() : ViewModel() {
    private val ordersRepo = OrdersRepo()
    val ordersMutableLiveData: MutableLiveData<OrderPojo> = MutableLiveData()

    fun getOrders() {
        ordersRepo.getOrders().enqueue(object : Callback<OrderPojo> {
            override fun onResponse(call: Call<OrderPojo>, response: Response<OrderPojo>) {
                if (response.isSuccessful && response.body() != null) {
                    ordersMutableLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<OrderPojo>, t: Throwable) {
                Timber.e("GetOrderFailure::${t.localizedMessage}")
            }
        })
    }
}