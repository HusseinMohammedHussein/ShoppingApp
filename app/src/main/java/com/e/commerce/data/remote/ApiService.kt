package com.e.commerce.data.remote

import com.e.commerce.data.model.CategoryDetailsPojo
import com.e.commerce.data.model.ProductDetailsPojo
import com.e.commerce.data.model.ProductsPojo
import com.e.commerce.data.model.ProfilePojo
import com.e.commerce.data.model.auth.*
import com.e.commerce.data.model.auth.OrderPojo.OrdersPojo
import com.e.commerce.data.model.auth.RegisterPojo.RequestRegisterPojo
import com.e.commerce.data.model.auth.bag.BagPojo
import com.e.commerce.data.model.auth.bag.BagResponsePojo
import com.e.commerce.data.model.auth.bag.BagUpdateResponsePojo
import com.e.commerce.data.model.auth.common.SignResponsePojo
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
    fun addOrRemoveFavorite(@Query("product_id") productId: Int): Call<FavoritePojo>

    @GET("favorites")
    fun getFavorites(): Call<FavoritePojo>

    @POST("carts")
    fun addOrRemoveBag(@Query("product_id") productId: Int): Call<BagPojo>

    @GET("carts")
    fun getBag(): Call<BagResponsePojo>

    @PUT("carts/{id}")
    fun updateQuantityBag(@Path("id") bagProductId: Int, @QueryMap hashMap: HashMap<String, Int>): Call<BagUpdateResponsePojo>

    @GET("orders")
    fun getOrders(): Call<OrderPojo>

    @GET("addresses")
    fun getAddresses(): Call<AddressPojo>

    @PUT("update-profile")
    fun settingProfile(@Body settingProfilePojo: SettingProfilePojo): Call<SignResponsePojo>
}