package com.e.commerce.ui.fragments.productdetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.commerce.data.model.ProductDetailsPojo
import com.e.commerce.data.model.ProductsPojo
import com.e.commerce.data.model.auth.bag.BagPojo
import com.e.commerce.data.model.auth.FavoritesPojo
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor() : ViewModel() {

    private val productDetailsRepo = ProductDetailsRepo()
    var productDetailsLiveData: MutableLiveData<ProductDetailsPojo> = MutableLiveData()
    var productsLiveData: MutableLiveData<ProductsPojo> = MutableLiveData()
    var addToBagLiveData: MutableLiveData<BagPojo> = MutableLiveData()
    var addToFavoritesLiveData: MutableLiveData<FavoritesPojo> = MutableLiveData()

    fun getProductDetails(id: Int) {
        productDetailsRepo.getProductDetails(id).enqueue(object : Callback<ProductDetailsPojo> {
            override fun onResponse(
                call: Call<ProductDetailsPojo>,
                response: Response<ProductDetailsPojo>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    productDetailsLiveData.value = response.body()
                    Timber.d("productsVM::${response.body()}")
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
                    Timber.d("productsVM::${response.body()}")
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
        productDetailsRepo.addToFavorites(productId).enqueue(object : Callback<FavoritesPojo> {
            override fun onResponse(call: Call<FavoritesPojo>, response: Response<FavoritesPojo>) {
                if (response.isSuccessful && response.body() != null) {
                    addToFavoritesLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<FavoritesPojo>, t: Throwable) {
                Timber.d("addFavoritesFailure::${t.localizedMessage}")
            }
        })
    }
}