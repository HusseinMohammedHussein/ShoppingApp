package com.e.commerce.ui.fragments.productdetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.commerce.data.model.ProductDetailsPojo
import com.e.commerce.data.model.ProductsPojo
import com.e.commerce.data.model.auth.bag.BagPojo
import com.e.commerce.data.model.auth.FavoritePojo
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor() : ViewModel() {

    private val productDetailsRepo = ProductDetailsRepo()
    val productDetailsLiveData: MutableLiveData<ProductDetailsPojo> = MutableLiveData()
    val productsLiveData: MutableLiveData<ProductsPojo> = MutableLiveData()
    val addToBagLiveData: MutableLiveData<BagPojo> = MutableLiveData()
    val addToFavoriteLiveData: MutableLiveData<FavoritePojo> = MutableLiveData()

    fun getProductDetails(id: Int) {
        productDetailsRepo.getProductDetails(id).enqueue(object : Callback<ProductDetailsPojo> {
            override fun onResponse(
                call: Call<ProductDetailsPojo>,
                response: Response<ProductDetailsPojo>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    productDetailsLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<ProductDetailsPojo>, t: Throwable) {
                Timber.d("ProductDetailFailure::${t.localizedMessage}")
            }

        })
    }

    fun getProducts() {
        productDetailsRepo.getProducts().enqueue(object : Callback<ProductsPojo> {
            override fun onResponse(
                call: Call<ProductsPojo>,
                response: Response<ProductsPojo>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    productsLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<ProductsPojo>, t: Throwable) {
                Timber.d("ProductDetailFailure::${t.localizedMessage}")
            }

        })
    }

    fun addToCart(productId: Int) {
        productDetailsRepo.addToCart(productId).enqueue(object : Callback<BagPojo> {
            override fun onResponse(call: Call<BagPojo>, response: Response<BagPojo>) {
                if (response.isSuccessful && response.body() != null) {
                    addToBagLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<BagPojo>, t: Throwable) {
                Timber.d("addToCartFailure::${t.localizedMessage}")
            }
        })
    }

    fun addToFavorites(productId: Int) {
        productDetailsRepo.addToFavorites(productId).enqueue(object : Callback<FavoritePojo> {
            override fun onResponse(call: Call<FavoritePojo>, response: Response<FavoritePojo>) {
                if (response.isSuccessful && response.body() != null) {
                    addToFavoriteLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<FavoritePojo>, t: Throwable) {
                Timber.d("addFavoritesFailure::${t.localizedMessage}")
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        addToFavoriteLiveData.value = null
        productDetailsLiveData.value = null
        productsLiveData.value = null
        addToBagLiveData.value = null
        Timber.d("addToFavoritesLiveData::OnClearedViewModel::${addToFavoriteLiveData.value}")
        Timber.d("productDetailsLiveData::OnClearedViewModel::${productDetailsLiveData.value}")
        Timber.d("productsLiveData::OnClearedViewModel::${productsLiveData.value}")
        Timber.d("addToBagLiveData::OnClearedViewModel::${addToBagLiveData.value}")
    }
}