package com.e.commerce.ui.fragments.user.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.commerce.data.model.auth.FavoritePojo
import com.e.commerce.ui.common.AddRemoveFavoriteRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor() : ViewModel() {
    private val favoritesRepo = FavoritesRepo()
    private val addRemoveFavoriteRepo = AddRemoveFavoriteRepo()
    val favoriteMData: MutableLiveData<FavoritePojo> = MutableLiveData()
    val removeFromFavoriteMData: MutableLiveData<FavoritePojo> = MutableLiveData()


    fun getFavorites() {
        favoritesRepo.getFavorites().enqueue(object : Callback<FavoritePojo> {
            override fun onResponse(call: Call<FavoritePojo>, response: Response<FavoritePojo>) {
                if (response.isSuccessful && response.body() != null) {
                    favoriteMData.value = response.body()
                }
            }

            override fun onFailure(call: Call<FavoritePojo>, t: Throwable) {
                Timber.d("getFavoritesFailure::${t.localizedMessage}")
            }

        })
    }

    fun removeFromFavorite(productId: Int) {
        addRemoveFavoriteRepo.addOrRemoveFavorite(productId).enqueue(object : Callback<FavoritePojo> {
            override fun onResponse(call: Call<FavoritePojo>, response: Response<FavoritePojo>) {
                if (response.isSuccessful && response.body() != null) {
                    removeFromFavoriteMData.value = response.body()
                }
            }

            override fun onFailure(call: Call<FavoritePojo>, t: Throwable) {
                Timber.d("removeFromCartFailure::${t.localizedMessage}")
            }
        })
    }
}