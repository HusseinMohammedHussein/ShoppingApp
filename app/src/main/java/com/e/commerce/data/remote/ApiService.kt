package com.e.commerce.data.remote

import com.e.commerce.data.model.auth.*
import com.e.commerce.data.model.auth.address.AddAddressPojo
import com.e.commerce.data.model.auth.address.AddressPojo
import com.e.commerce.data.model.auth.address.AddressesDataPojo.AddressDataPojo
import com.e.commerce.data.model.auth.bag.BagItemAddRemovePojo
import com.e.commerce.data.model.auth.bag.BagProductsPojo
import com.e.commerce.data.model.auth.bag.BagUpdatePojo
import com.e.commerce.data.model.auth.favorite.FavoritePojo
import com.e.commerce.data.model.auth.login.LoginDataPojo
import com.e.commerce.data.model.auth.login.LoginPojo
import com.e.commerce.data.model.auth.order.AddOrderPojo
import com.e.commerce.data.model.auth.order.OrderAddedResponsePojo
import com.e.commerce.data.model.auth.order.OrderDetailsPojo
import com.e.commerce.data.model.auth.order.OrderPojo
import com.e.commerce.data.model.auth.profile.ProfilePojo
import com.e.commerce.data.model.auth.register.RegisterDataPojo
import com.e.commerce.data.model.auth.register.RegisterPojo
import com.e.commerce.data.model.category.CategoryPojo
import com.e.commerce.data.model.category.CategoryProductsPojo
import com.e.commerce.data.model.common.SignResponsePojo
import com.e.commerce.data.model.home.HomePojo
import com.e.commerce.data.model.product.ProductDetailsPojo
import com.e.commerce.data.model.product.ProductsPojo
import retrofit2.Call
import retrofit2.http.*

// Created by Hussein_Mohammad on 5/2/2021.

interface ApiService {

    /*
    ــــــــــــــــــــــــــ Home ــــــــــــــــــــ*
    */

    @GET("home")
    fun getHomeData(): Call<HomePojo>

    /*
    ــــــــــــــــــــــــــ Categories ــــــــــــــــــــ*
    */

    @GET("categories")
    fun getCategories(): Call<CategoryPojo>

    @GET("categories/{id}")
    fun getCategoryProducts(@Path("id") id: Int): Call<CategoryProductsPojo>

    /*
    ــــــــــــــــــــــــــ Products ــــــــــــــــــــ*
    */

    @GET("products")
    fun getProducts(): Call<ProductsPojo>

    @GET("products/{id}")
    fun getProductDetails(@Path("id") id: Int): Call<ProductDetailsPojo>

    @POST("products/search")
    fun searchProduct(@Query("text") keyword: String): Call<ProductsPojo>

    /*
    ــــــــــــــــــــــــــ Auth ــــــــــــــــــــ*
    */

    @POST("register")
    fun register(@Body registerData: RegisterDataPojo): Call<RegisterPojo>

    @POST("login")
    fun login(@Body loginPojo: LoginDataPojo): Call<LoginPojo>

    @POST("logout")
    fun logout(@Query("fcm_token") fcmToken: String): Call<LogoutPojo>

    @GET("profile")
    fun getProfile(): Call<ProfilePojo>

    @PUT("update-profile")
    fun settingProfile(@Body settingProfilePojo: SettingProfilePojo): Call<SignResponsePojo>

    @POST("notifications")
    fun getNotifies(): Call<NotificationsPojo>

    @POST("fcm-token")
    fun setFCMToken(@Query("token") token: String): Call<FCMTokenPojo>

    /*
    ــــــــــــــــــــــــــ Favorites ــــــــــــــــــــ*
    */

    @POST("favorites")
    fun addOrRemoveFavorite(@Query("product_id") productId: Int): Call<FavoritePojo>

    @GET("favorites")
    fun getFavorites(): Call<FavoritePojo>

    /*
    ــــــــــــــــــــــــــ Carts | Bags ــــــــــــــــــــ*
    */

    @GET("carts")
    fun getBagProducts(): Call<BagProductsPojo>

    @POST("carts")
    fun addOrRemoveBag(@Query("product_id") productId: Int): Call<BagItemAddRemovePojo>

    @PUT("carts/{id}")
    fun updateQuantityBag(@Path("id") bagProductId: Int, @QueryMap hashMap: HashMap<String, Int>): Call<BagUpdatePojo>

    /*
    ــــــــــــــــــــــــــ Orders ــــــــــــــــــــ*
    */

    @GET("orders")
    fun getOrders(): Call<OrderPojo>

    @POST("orders")
    fun addOrder(@Body order: AddOrderPojo): Call<OrderAddedResponsePojo>

    @GET("orders/{order_id}")
    fun getOrderDetails(@Path("order_id") id: Int): Call<OrderDetailsPojo>

    /*
    ــــــــــــــــــــــــــ Addresses ــــــــــــــــــــ*
    */

    @GET("addresses")
    fun getAddresses(): Call<AddressPojo>

    @POST("addresses")
    fun addAddresses(@Body addAddress: AddAddressPojo): Call<AddressPojo> // AddressDataPojo

    @DELETE("addresses/{id}")
    fun deleteAddresses(@Path("id") addressId: Int): Call<AddressPojo>

    @PUT("addresses/{id}")
    fun updateAddresses(@Path("id") addressId: Int, @Body updateAddress: AddressDataPojo): Call<AddressPojo>

    /*
    ــــــــــــــــــــــــــ PromoCodes ــــــــــــــــــــ*
    */

    @POST("promo-codes/validate")
    fun checkPromoCode(@Query("code") code: String): Call<PromocodePojo>
}