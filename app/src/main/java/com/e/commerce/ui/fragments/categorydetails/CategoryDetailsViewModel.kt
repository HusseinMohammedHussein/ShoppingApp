package com.e.commerce.ui.fragments.categorydetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.commerce.data.model.CategoryDetailsPojo
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CategoryDetailsViewModel @Inject constructor() : ViewModel() {
    private val catDetailsRepo = CategoryDetailsRepo()
    var categoryDetailsMutable: MutableLiveData<CategoryDetailsPojo> = MutableLiveData()


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
}