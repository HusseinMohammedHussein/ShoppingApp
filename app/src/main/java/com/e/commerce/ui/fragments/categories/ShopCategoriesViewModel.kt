package com.e.commerce.ui.fragments.categories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.commerce.data.model.ShopCategoryPojo
import com.e.commerce.data.model.home.CategoryPojo
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ShopCategoriesViewModel @Inject constructor() : ViewModel() {
    private var shopCategoryRepo = ShopCategoryRepo()
    var shopCategoryMutable: MutableLiveData<CategoryPojo> = MutableLiveData()


    fun getShopCategories() {
        shopCategoryRepo.getShopCategory().enqueue(object : Callback<CategoryPojo> {
            override fun onResponse(
                call: Call<CategoryPojo>,
                response: Response<CategoryPojo>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    shopCategoryMutable.value = response.body()
                }
            }

            override fun onFailure(call: Call<CategoryPojo>, t: Throwable) {
                Timber.e("getShopCategories::${t.localizedMessage}")
            }
        })
    }
}