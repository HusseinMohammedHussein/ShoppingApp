package com.e.commerce.ui.fragments.user.bag

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.commerce.data.model.auth.favorite.FavoritePojo
import com.e.commerce.data.model.auth.PromocodePojo
import com.e.commerce.data.model.auth.bag.BagItemAddRemovePojo
import com.e.commerce.data.model.auth.bag.BagProductsPojo
import com.e.commerce.data.model.auth.bag.BagUpdatePojo
import com.e.commerce.ui.component.AddRemoveFavoriteRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class BagViewModel : ViewModel() {
    private val bagRepo = BagRepo()
    private val addRemoveFavoriteRepo = AddRemoveFavoriteRepo()
    val bagMutableData: MutableLiveData<BagProductsPojo> = MutableLiveData()
    val quantityBagUpdateMutableData: MutableLiveData<BagUpdatePojo> = MutableLiveData()

    fun getBag() {
        bagRepo.getBag().enqueue(object : Callback<BagProductsPojo> {
            override fun onResponse(call: Call<BagProductsPojo>, products: Response<BagProductsPojo>) {
                if (products.isSuccessful && products.body() != null) {
                    bagMutableData.value = products.body()
                }
            }

            override fun onFailure(call: Call<BagProductsPojo>, t: Throwable) {
                Timber.e("getBagFailure::${t.localizedMessage}")
            }
        })
    }

    fun updateQuantityBag(bagProductId: Int, hashMap: HashMap<String, Int>) {
        bagRepo.updateQuantityBag(bagProductId, hashMap).enqueue(object : Callback<BagUpdatePojo> {
            override fun onResponse(call: Call<BagUpdatePojo>, update: Response<BagUpdatePojo>) {
                if (update.isSuccessful && update.body() != null) {
                    quantityBagUpdateMutableData.value = update.body()
                }
            }

            override fun onFailure(call: Call<BagUpdatePojo>, t: Throwable) {
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

    fun removeBagProduct(productId: Int): MutableLiveData<BagItemAddRemovePojo> {
        val removeBagItemAddRemoveProductMutableData: MutableLiveData<BagItemAddRemovePojo> = MutableLiveData()
        bagRepo.removeBagProduct(productId).enqueue(object : Callback<BagItemAddRemovePojo> {
            override fun onResponse(call: Call<BagItemAddRemovePojo>, response: Response<BagItemAddRemovePojo>) {
                if (response.isSuccessful && response.body() != null) {
                    removeBagItemAddRemoveProductMutableData.value = response.body()
                }
            }

            override fun onFailure(call: Call<BagItemAddRemovePojo>, t: Throwable) {
                Timber.e("RemoveProductFromBagFailure::${t.localizedMessage}")
            }
        })
        return removeBagItemAddRemoveProductMutableData
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