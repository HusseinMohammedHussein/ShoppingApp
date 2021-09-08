package com.e.commerce.ui.fragments.categorydetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.commerce.data.model.category.CategoryProductsPojo
import com.e.commerce.data.model.product.ProductsPojo
import com.e.commerce.data.model.auth.favorite.FavoritePojo
import com.e.commerce.ui.component.AddRemoveFavoriteRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

//@HiltViewModel
class CategoryProductsVM : ViewModel() {
    private val catDetailsRepo = CategoryProductsRepo()
    private val addRemoveFavoriteRepo = AddRemoveFavoriteRepo()
    var categoryProductsMutable: MutableLiveData<CategoryProductsPojo> = MutableLiveData()
    var searchProductMutable: MutableLiveData<ProductsPojo> = MutableLiveData()
    var addRemoveFavoriteMutable: MutableLiveData<FavoritePojo> = MutableLiveData()

    fun getCategoryProducts(id: Int) {
        catDetailsRepo.getCategoryProducts(id).enqueue(object : Callback<CategoryProductsPojo> {
            override fun onResponse(
                call: Call<CategoryProductsPojo>,
                response: Response<CategoryProductsPojo>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    categoryProductsMutable.value = response.body()
                }
            }

            override fun onFailure(call: Call<CategoryProductsPojo>, t: Throwable) {
                Timber.d("categoryDetailsFailure::${t.localizedMessage}")
            }

        })
    }

    fun searchProduct(keyword: String) {
        catDetailsRepo.searchProduct(keyword).enqueue(object : Callback<ProductsPojo> {
            override fun onResponse(call: Call<ProductsPojo>, response: Response<ProductsPojo>) {
                if (response.isSuccessful && response.body() != null) {
                    searchProductMutable.value = response.body()
                }
            }

            override fun onFailure(call: Call<ProductsPojo>, t: Throwable) {
                Timber.d("searchProductFailure::${t.localizedMessage}")
            }

        })
    }

    fun addRemoveFavorite(productId: Int) {
        addRemoveFavoriteRepo.addOrRemoveFavorite(productId).enqueue(object : Callback<FavoritePojo> {
            override fun onResponse(call: Call<FavoritePojo>, response: Response<FavoritePojo>) {
                if (response.isSuccessful && response.body() != null) {
                    addRemoveFavoriteMutable.value = response.body()
                }
            }

            override fun onFailure(call: Call<FavoritePojo>, t: Throwable) {
                Timber.d("addRemoveFavoriteFailure::${t.localizedMessage}")
            }
        })
    }
}