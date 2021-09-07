package com.e.commerce.ui.fragments.user.orders.ordersRecyclerView

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.commerce.data.model.auth.OrderPojo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class OrdersViewModel : ViewModel() {
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