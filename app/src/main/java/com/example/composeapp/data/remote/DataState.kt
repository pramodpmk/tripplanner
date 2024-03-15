package com.example.composeapp.data.remote

import okhttp3.ResponseBody

sealed class DataState<out R> {

    data class Success<out T>(val data: T): DataState<T>()
    data class Error(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?,
        val errorType: String?,
        val message: String?
    ): DataState<Nothing>()
}