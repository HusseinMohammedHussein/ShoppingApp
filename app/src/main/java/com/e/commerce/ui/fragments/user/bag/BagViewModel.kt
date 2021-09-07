package com.e.commerce.ui.fragments.user.bag

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.commerce.data.model.auth.FavoritePojo
import com.e.commerce.data.model.auth.PromocodePojo
import com.e.commerce.data.model.auth.bag.BagPojo
import com.e.commerce.data.model.auth.bag.BagResponsePojo
import com.e.commerce.data.model.auth.bag.BagUpdateResponsePojo
import com.e.commerce.ui.common.AddRemoveFavoriteRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class BagViewModel : ViewModel() {
    private val bagRepo = BagRepo()
    private val addRemoveFavoriteRepo = AddRemoveFavoriteRepo()
    val bagMutableData: MutableLiveData<BagResponsePojo> = MutableLiveData()
    val quantityBagUpdateMutableData: MutableLiveData<BagUpdateResponsePojo> = MutableLiveData()

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

    fun addRemoveFavorite(productId: Int): MutableLiveData<FavoritePojo> {
        val addRemoveFavoriteMutableData: MutableLiveData<FavoritePojo> = MutableLiveData()
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
        return addRemoveFavoriteMutableData
    }

    fun removeBagProduct(productId: Int): MutableLiveData<BagPojo> {
        val removeBagProductMutableData: MutableLiveData<BagPojo> = MutableLiveData()
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
        return removeBagProductMutableData
    }

    fun checkPromoCode(code: String): MutableLiveData<PromocodePojo> {
        val checkPromoCode: MutableLiveData<PromocodePojo> = MutableLiveData()
        bagRepo.checkPromoCode(code).enqueue(object : Callback<PromocodePojo> {
            override fun onResponse(call: Call<PromocodePojo>, response: Response<PromocodePojo>) {
                if (response.isSuccessful && response.body() != null) {
                    checkPromoCode.value = response.body()
                }
            }

            override fun onFailure(call: Call<PromocodePojo>, t: Throwable) {
                Timber.e("checkPromoCodeFailure::${t.localizedMessage}")
            }
        })
        return checkPromoCode
    }
}