package com.example.composeapp.data.remote

data class ErrorResponse(
    val error: ErrorData?,
    val info: ErrorData?
)

data class ErrorData(
    val code: Int?,
    val message: String?,
    val type: String?
)