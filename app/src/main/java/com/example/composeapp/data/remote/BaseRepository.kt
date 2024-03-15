package com.example.composeapp.data.remote

import com.example.composeapp.utils.InternetUtils
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response


abstract class BaseRepository {

    abstract fun getNetworkUtils(): InternetUtils


    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): DataState<T> {
        return withContext(Dispatchers.IO) {
            try {
                if (getNetworkUtils().checkForInternet()) {
                    DataState.Success(apiCall.invoke())
                } else {
                    throw Exception("No internet")
                }
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                when (throwable) {
                    is HttpException -> {
                        val errorBody = throwable.response()?.errorBody()?.string()
                        println("ERROR BODY>>>>>>>$errorBody")
                        val message: String = getErrorMessage(errorBody)
                        val errorRespCode: Int = getErrorCode(errorBody, throwable.code())
                        val errorType: String = getErrorType(errorBody)
                        val errorCode: Int = throwable.code()
                        DataState.Error(
                            false,
                            errorCode,
                            throwable.response()?.errorBody(),
                            errorType,
                            message
                        )
                    }

                    else -> {
                        DataState.Error(
                            true,
                            null,
                            null,
                            null,
                            "Unable to reach the servers, Please contact our customer service"
                        )
                    }
                }
            }
        }
    }

    private fun getErrorMessage(str: String?): String {
        return try {
            //val str: String = response?.errorBody()?.string()!!
            val errorRes: ErrorResponse? = Gson().fromJson(
                str, ErrorResponse::class.java
            )
            val message: String = when {
                errorRes?.info?.message != null -> {
                    errorRes.info.message
                }
                errorRes?.error?.message != null -> {
                    errorRes.error.message
                }
                else -> {
                    "Unable to reach the servers, Please contact our customer service"
                }
            }
            message
        } catch (e: Exception) {
            e.printStackTrace()
            "Unable to reach the servers, Please contact our customer service"
        }
    }

    private fun getErrorCode(str: String?, respCode: Int): Int {
        return try {
            val errorRes: ErrorResponse? = Gson().fromJson(
                str, ErrorResponse::class.java
            )
            val code: Int = when {
                errorRes?.error?.code != null -> {
                    errorRes.error.code
                }
                errorRes?.info?.code != null -> {
                    errorRes.info.code
                }
                else -> {
                    respCode
                }
            }
            code
        } catch (e: Exception) {
            e.printStackTrace()
            respCode
        }
    }

    private fun getErrorType(str: String?): String {
        return try {
            val errorRes: ErrorResponse? = Gson().fromJson(
                str, ErrorResponse::class.java
            )
            val code: String = when {
                errorRes?.error?.type != null -> {
                    errorRes.error.type
                }
                errorRes?.info?.type != null -> {
                    errorRes.info.type
                }
                else -> {
                    ""
                }
            }
            code
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    suspend fun logout() {
       clearData()
    }

    private suspend fun clearData() {
        // Clear data
    }
}