package com.e.commerce.data.remote

import com.e.commerce.data.model.CategoryDetailsPojo
import com.e.commerce.data.model.ProductDetailsPojo
import com.e.commerce.data.model.ProductsPojo
import com.e.commerce.data.model.ProfilePojo
import com.e.commerce.data.model.auth.*
import com.e.commerce.data.model.auth.AddressPojo.AddAddressPojo
import com.e.commerce.data.model.auth.AddressPojo.AddressDataPojo.AddressObjectPojo
import com.e.commerce.data.model.auth.LoginPojo.LoginDataPojo
import com.e.commerce.data.model.auth.OrderPojo.AddOrderPojo
import com.e.commerce.data.model.auth.OrderPojo.OrderResponsePojo
import com.e.commerce.data.model.auth.RegisterPojo.RegisterDataPojo
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
    fun register(@Body registerData: RegisterDataPojo): Call<RegisterPojo>

    @POST("login")
    fun login(@Body loginPojo: LoginDataPojo): Call<LoginPojo>

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

    @POST("orders")
    fun addOrder(@Body order: AddOrderPojo): Call<OrderResponsePojo>

    @GET("orders/{order_id}")
    fun getOrderDetails(@Path("order_id") id: Int): Call<OrderDetailsPojo>

    @GET("addresses")
    fun getAddresses(): Call<AddressPojo>

    @POST("addresses")
    fun addAddresses(@Body addAddress: AddAddressPojo): Call<AddressPojo>

    @DELETE("addresses/{id}")
    fun deleteAddresses(@Path("id") addressId: Int): Call<AddressPojo>

    @PUT("addresses/{id}")
    fun updateAddresses(@Path("id") addressId: Int, @Body updateAddress: AddressObjectPojo): Call<AddressPojo>

    @PUT("update-profile")
    fun settingProfile(@Body settingProfilePojo: SettingProfilePojo): Call<SignResponsePojo>

    @POST("promo-codes/validate")
    fun checkPromoCode(@Query("code") code: String): Call<PromocodePojo>
}