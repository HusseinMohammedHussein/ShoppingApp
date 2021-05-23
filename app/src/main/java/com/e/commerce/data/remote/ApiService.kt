package com.e.commerce.data.remote

import com.e.commerce.data.model.*
import com.e.commerce.data.model.RegisterPojo.RequestRegisterPojo
import com.e.commerce.data.model.auth.FavoritesPojo
import com.e.commerce.data.model.auth.bag.BagPojo
import com.e.commerce.data.model.auth.bag.BagsPojo
import com.e.commerce.data.model.auth.bag.UpdateBagResponsePojo
import com.e.commerce.data.model.home.CategoryPojo
import com.e.commerce.data.model.home.HomePojo
import retrofit2.Call
import retrofit2.http.*

// Created by Hussein_Mohammad on 5/2/2021.

interface ApiService {

    @GET("home")
    fun getHomeData(): Call<HomePojo>

    @GET("categories")
    fun getCategory(): Call<CategoryPojo>

    @GET("categories")
    fun getShopCategory(): Call<CategoryPojo>

    @GET("categories/{id}")
    fun getCategoryDetails(@Path("id") id: Int): Call<CategoryDetailsPojo>

    @GET("products/{id}")
    fun getProductDetails(@Path("id") id: Int): Call<ProductDetailsPojo>

    @GET("products")
    fun getProducts(): Call<ProductsPojo>


    @POST("products/search")
    fun searchProduct(@Query("text") keyword: String): Call<ProductsPojo>


    /**Auth________________________________________________________________________*/
    @POST("register")
    fun requestRegister(@Body requestRegister: RequestRegisterPojo): Call<RegisterPojo>

    @GET("profile")
    fun getProfile(): Call<ProfilePojo>

    @POST("favorites")
    fun addOrRemoveFavorite(@Query("product_id") productId: Int): Call<FavoritesPojo>

    @GET("favorites")
    fun getFavorites(): Call<FavoritesPojo>

    @POST("carts")
    fun addOrRemoveBag(@Query("product_id") productId: Int): Call<BagPojo>

    @GET("carts")
    fun getBag(): Call<BagsPojo>

    @PUT("carts/{id}")
    fun updateQuantityBag(@Path("id") bagProductId: Int, @QueryMap hashMap: HashMap<String, Int>): Call<UpdateBagResponsePojo>
}