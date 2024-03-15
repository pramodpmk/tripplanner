package com.example.composeapp.data


sealed class UiState<out R> {

    data class Success<out T>(val data: T): UiState<T>()
    data class Error(
        val errorType: Int,
        val message: String
    ): UiState<Nothing>()
    object Loading : UiState<Nothing>()
    object Idle : UiState<Nothing>()
}