package com.example.composeapp.di

import com.example.composeapp.data.remote.AuthInterceptor
import com.example.composeapp.data.remote.ChatGptService
import com.example.composeapp.data.remote.ErrorHandlerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    const val STAGE_BASE_URL = "https://api.openai.com/"
    const val CHATGPT_API_KEY = "my-dummy-key"
    const val API_VERSION = "v1/"

    @Provides
    @Named("client")
    fun provideClient(
        errorHandlerInterceptor: ErrorHandlerInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(errorHandlerInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

//    @Provides
//    @Named("oauth2_client")
//    fun provideOAuthTwoClient(
//        authInterceptor: AuthInterceptor,
//        errorHandlerInterceptor: ErrorHandlerInterceptor,
//        localizationInterceptor: LocalizationInterceptor,
//        tokenAuthenticator: TokenAuthenticator
//    ): OkHttpClient {
//        val loggingInterceptor = HttpLoggingInterceptor()
//        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        val userAgentInterceptor = UserAgentInterceptor()
//        return OkHttpClient.Builder()
//            .authenticator(tokenAuthenticator)
//            .addInterceptor(errorHandlerInterceptor)
//            .addInterceptor(authInterceptor)
//            .addInterceptor(localizationInterceptor)
//            .addInterceptor(userAgentInterceptor)
//            .addInterceptor(loggingInterceptor)
//            .build()
//    }

    @Provides
    @Singleton
    fun provideSearchApi(
        @Named("client") client: OkHttpClient
    ): ChatGptService {
        return provideService(
            okhttpClient = client,
            clazz = ChatGptService::class.java)
    }

    private fun createRetrofit(
        okhttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(STAGE_BASE_URL)
            .client(okhttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun <T> provideService(
        okhttpClient: OkHttpClient,
        clazz: Class<T>
    ): T {
        return createRetrofit(okhttpClient).create(clazz)
    }
}
