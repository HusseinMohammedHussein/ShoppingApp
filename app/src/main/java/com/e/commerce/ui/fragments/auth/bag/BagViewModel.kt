package com.e.commerce.ui.fragments.auth.bag

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.commerce.data.model.auth.FavoritePojo
import com.e.commerce.data.model.auth.bag.BagPojo
import com.e.commerce.data.model.auth.bag.BagResponsePojo
import com.e.commerce.data.model.auth.bag.BagUpdateResponsePojo
import com.e.commerce.ui.common.AddRemoveFavoriteRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BagViewModel @Inject constructor() : ViewModel() {
    private val bagRepo = BagRepo()
    private val addRemoveFavoriteRepo = AddRemoveFavoriteRepo()
    val bagMutableData: MutableLiveData<BagResponsePojo> = MutableLiveData()
    val quantityBagUpdateMutableData: MutableLiveData<BagUpdateResponsePojo> = MutableLiveData()
    val addRemoveFavoriteMutableData: MutableLiveData<FavoritePojo> = MutableLiveData()
    val removeBagProductMutableData: MutableLiveData<BagPojo> = MutableLiveData()

    fun getBag() {
        bagRepo.getBag().enqueue(object : Callback<BagResponsePojo> {
            override fun onResponse(call: Call<BagResponsePojo>, response: Response<BagResponsePojo>) {
                if (response.isSuccessful && response.body() != null) {
                    bagMutableData.value = response.body()
                }
            }

            override fun onFailure(call: Call<BagResponsePojo>, t: Throwable) {
                Timber.e("getBagFailure::${t.localizedMessage}")
            }
        })
    }

    fun updateQuantityBag(bagProductId: Int, hashMap: HashMap<String, Int>) {
        bagRepo.updateQuantityBag(bagProductId, hashMap).enqueue(object : Callback<BagUpdateResponsePojo> {
            override fun onResponse(call: Call<BagUpdateResponsePojo>, updateResponse: Response<BagUpdateResponsePojo>) {
                if (updateResponse.isSuccessful && updateResponse.body() != null) {
                    quantityBagUpdateMutableData.value = updateResponse.body()
                }
            }

            override fun onFailure(call: Call<BagUpdateResponsePojo>, t: Throwable) {
                Timber.e("getUpdateBagFailure:: ${t.localizedMessage}")
            }
        })
    }

    fun addRemoveFavorite(productId: Int) {
        addRemoveFavoriteRepo.addOrRemoveFavorite(productId).enqueue(object : Callback<FavoritePojo> {
            override fun onResponse(call: Call<FavoritePojo>, response: Response<FavoritePojo>) {
                if (response.isSuccessful && response.body() != null) {
                    addRemoveFavoriteMutableData.value = response.body()
                }
            }

            override fun onFailure(call: Call<FavoritePojo>, t: Throwable) {
                Timber.e("addRemoveFavoriteFailure::${t.localizedMessage}")
            }
        })
    }

    fun removeBagProduct(productId: Int) {
        bagRepo.removeBagProduct(productId).enqueue(object : Callback<BagPojo> {
            override fun onResponse(call: Call<BagPojo>, response: Response<BagPojo>) {
                if (response.isSuccessful && response.body() != null) {
                    removeBagProductMutableData.value = response.body()
                }
            }

            override fun onFailure(call: Call<BagPojo>, t: Throwable) {
                Timber.e("RemoveProductFromBagFailure::${t.localizedMessage}")
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        bagMutableData.value = null
        quantityBagUpdateMutableData.value = null
        addRemoveFavoriteMutableData.value = null
        removeBagProductMutableData.value = null
    }
}