package com.example.composeapp.data.remote

import com.example.composeapp.di.NetworkModule
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        println("Inside Auth Interceptor")
        val requestBuilder = chain.request().newBuilder()
        val token = NetworkModule.CHATGPT_API_KEY
        println("Inside Auth Interceptor Token = $token")
        if (token.isNotEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
            requestBuilder.addHeader("Content-Type", "application/json")
        }
        return chain.proceed(requestBuilder.build())
    }
}