package com.e.commerce.ui.fragments.auth.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.commerce.data.model.auth.FavoritesPojo
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor() : ViewModel() {
    private val favoritesRepo = FavoritesRepo()
    val favoritesMData: MutableLiveData<FavoritesPojo> = MutableLiveData()
    val removeFromFavoritesMData: MutableLiveData<FavoritesPojo> = MutableLiveData()


    fun getFavorites() {
        favoritesRepo.getFavorites().enqueue(object : Callback<FavoritesPojo> {
            override fun onResponse(call: Call<FavoritesPojo>, response: Response<FavoritesPojo>) {
                if (response.isSuccessful && response.body() != null) {
                    favoritesMData.value = response.body()
                }
            }

            override fun onFailure(call: Call<FavoritesPojo>, t: Throwable) {
                Timber.d("getFavoritesFailure::${t.localizedMessage}")
            }

        })
    }

    fun removeFromFavorite(productId: Int) {
        favoritesRepo.removeFromFavorite(productId).enqueue(object : Callback<FavoritesPojo> {
            override fun onResponse(call: Call<FavoritesPojo>, response: Response<FavoritesPojo>) {
                if (response.isSuccessful && response.body() != null) {
                    removeFromFavoritesMData.value = response.body()
                }
            }

            override fun onFailure(call: Call<FavoritesPojo>, t: Throwable) {
                Timber.d("removeFromCartFailure::${t.localizedMessage}")
            }
        })
    }
}