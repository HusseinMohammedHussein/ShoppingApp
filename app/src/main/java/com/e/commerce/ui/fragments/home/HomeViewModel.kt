package com.e.commerce.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.commerce.data.model.auth.FavoritePojo
import com.e.commerce.data.model.home.CategoryPojo
import com.e.commerce.data.model.home.HomePojo
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val homeRepo = HomeRepo()
    val homePojoMutable: MutableLiveData<HomePojo> = MutableLiveData()
    val categoryPojoMutable: MutableLiveData<CategoryPojo> = MutableLiveData()

    fun getHomeLiveData() {
        homeRepo.getHome().enqueue(object : Callback<HomePojo> {
            override fun onResponse(call: Call<HomePojo>, response: Response<HomePojo>) {
                if (response.isSuccessful && response.body() != null) {
                    homePojoMutable.value = response.body()
                }
            }

            override fun onFailure(call: Call<HomePojo>, t: Throwable) {
                Timber.d("HomeFailure:: ${t.localizedMessage}")
            }
        })
    }

    fun getCategoriesLiveData() {
        homeRepo.getCategories().enqueue(object : Callback<CategoryPojo> {
            override fun onResponse(call: Call<CategoryPojo>, response: Response<CategoryPojo>) {
                if (response.isSuccessful && response.body() != null) {
                    categoryPojoMutable.value = response.body()
                }
            }

            override fun onFailure(call: Call<CategoryPojo>, t: Throwable) {
                Timber.d("GetCategoriesFailure::${t.localizedMessage}")
            }

        })
    }

    fun addOrRemoveFromFavorite(productId: Int) : LiveData<FavoritePojo> {
        val addOrRemoveFromFavoriteMutable: MutableLiveData<FavoritePojo> = MutableLiveData()
        homeRepo.addFavorite(productId).enqueue(object : Callback<FavoritePojo> {
            override fun onResponse(call: Call<FavoritePojo>, response: Response<FavoritePojo>) {
                if (response.isSuccessful && response.body() != null) {
                    addOrRemoveFromFavoriteMutable.value = response.body()
                }
            }
            override fun onFailure(call: Call<FavoritePojo>, t: Throwable) {
                Timber.d("addFavoriteFailure::${t.localizedMessage}")
            }
        })
        return  addOrRemoveFromFavoriteMutable
    }
}