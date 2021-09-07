package com.e.commerce.data.remote.retrofit

import com.e.commerce.CommerceApplication
import com.e.commerce.R
import com.e.commerce.util.SharedPref
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber

// Created by Hussein_Mohammad on 5/6/2021.

class HeaderInterceptor : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val sharedPref = CommerceApplication.getContext()?.let { SharedPref(it) }
        val getUserToken = CommerceApplication.getContext()?.resources?.getString(R.string.user_token)?.let { sharedPref?.getString(it) }


        val request: Request = chain.request()
        val requestBuilder: Request.Builder = request.newBuilder()

        requestBuilder.addHeader("Content-Type", "application/json")
        requestBuilder.addHeader("lang", "en")
        if (getUserToken!!.isNotEmpty()) {
            requestBuilder.addHeader("Authorization", getUserToken)
            Timber.d("Header_UserToken::${getUserToken}")
        }
        return chain.proceed(requestBuilder.build())
    }
}