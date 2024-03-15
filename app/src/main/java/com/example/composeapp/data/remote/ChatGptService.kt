package com.example.composeapp.data.remote

import com.example.composeapp.data.search.SearchRequest
import com.example.composeapp.data.search.SearchResponse
import com.example.composeapp.di.NetworkModule
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatGptService {

    @POST("${NetworkModule.API_VERSION}chat/completions")
    suspend fun search(@Body request: SearchRequest): SearchResponse

}