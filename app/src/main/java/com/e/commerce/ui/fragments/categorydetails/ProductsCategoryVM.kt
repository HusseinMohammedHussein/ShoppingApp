package com.e.commerce.ui.fragments.categorydetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.commerce.data.model.CategoryDetailsPojo
import com.e.commerce.data.model.ProductsPojo
import com.e.commerce.data.model.auth.FavoritePojo
import com.e.commerce.ui.common.AddRemoveFavoriteRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

//@HiltViewModel
class ProductsCategoryVM : ViewModel() {
    private val catDetailsRepo = CategoryDetailsRepo()
    private val addRemoveFavoriteRepo = AddRemoveFavoriteRepo()
    var categoryDetailsMutable: MutableLiveData<CategoryDetailsPojo> = MutableLiveData()
    var searchProductMutable: MutableLiveData<ProductsPojo> = MutableLiveData()
    var addRemoveFavoriteMutable: MutableLiveData<FavoritePojo> = MutableLiveData()


    fun getCategoryDetails(id: Int) {
        catDetailsRepo.getCategoryDetails(id).enqueue(object : Callback<CategoryDetailsPojo> {
            override fun onResponse(
                call: Call<CategoryDetailsPojo>,
                response: Response<CategoryDetailsPojo>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    categoryDetailsMutable.value = response.body()
                }
            }

            override fun onFailure(call: Call<CategoryDetailsPojo>, t: Throwable) {
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