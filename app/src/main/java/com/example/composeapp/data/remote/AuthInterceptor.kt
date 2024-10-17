package com.example.composeapp.data.remote

import com.example.composeapp.data.login.ChatSettingsRepository
import com.example.composeapp.di.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    val chatSettingsRepository: ChatSettingsRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        println("Inside Auth Interceptor")
        val requestBuilder = chain.request().newBuilder()
        val token = runBlocking {
            getChatGptKey()
        }
        println("Inside Auth Interceptor Token = $token")
        if (token.isNotEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
            requestBuilder.addHeader("Content-Type", "application/json")
        }
        return chain.proceed(requestBuilder.build())
    }

    private suspend fun getChatGptKey(): String = withContext(Dispatchers.IO) {
        chatSettingsRepository.getGptSettings()?.api_key ?: ""
    }
}