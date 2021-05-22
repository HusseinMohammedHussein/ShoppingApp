package com.e.commerce.ui.fragments.auth.bag

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.commerce.data.model.auth.bag.BagsPojo
import com.e.commerce.data.model.auth.bag.UpdateBagResponsePojo
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BagViewModel @Inject constructor() : ViewModel() {
    private val bagRepo = BagRepo()
    var bagData: MutableLiveData<BagsPojo> = MutableLiveData()
    var updateBagData: MutableLiveData<UpdateBagResponsePojo> = MutableLiveData()

    fun getBag() {
        bagRepo.getBag().enqueue(object : Callback<BagsPojo> {
            override fun onResponse(call: Call<BagsPojo>, response: Response<BagsPojo>) {
                if (response.isSuccessful && response.body() != null) {
                    bagData.value = response.body()
                }
            }

            override fun onFailure(call: Call<BagsPojo>, t: Throwable) {
                Timber.d("getBagFailure::${t.localizedMessage}")
            }
        })
    }

    fun updateBag(bagProductId: Int, hashMap: HashMap<String, Int>) {
        bagRepo.updateBag(bagProductId, hashMap).enqueue(object : Callback<UpdateBagResponsePojo> {
            override fun onResponse(call: Call<UpdateBagResponsePojo>, response: Response<UpdateBagResponsePojo>) {
                if (response.isSuccessful && response.body() != null) {
                    updateBagData.value = response.body()
                }
            }

            override fun onFailure(call: Call<UpdateBagResponsePojo>, t: Throwable) {
                Timber.d("getUpdateBagFailure:: ${t.localizedMessage}")
            }
        })
    }
}