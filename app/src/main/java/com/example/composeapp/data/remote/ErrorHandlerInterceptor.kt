package com.example.composeapp.data.remote


import android.content.Context
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import okio.BufferedSource
import java.nio.charset.Charset
import javax.inject.Inject

class ErrorHandlerInterceptor @Inject constructor(val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        var response = chain.proceed(requestBuilder.build())
        try {
            when (response.code) {
                400 -> {
                    val errorResp = toResponseString(response)
                    val contentType: MediaType? = response.body?.contentType()
                    val body: ResponseBody = errorResp.toResponseBody(contentType)
                    response = response.newBuilder().body(body).build()
                }
                else -> {
                    // Do nothing
                }
            }
        } catch (e: Exception) {
            return response
        }
        return response

    }

    private fun toResponseString(response: Response): String {
        val source: BufferedSource? = response.body?.source()
        source?.request(Long.MAX_VALUE) // Buffer the entire body.

        val buffer: Buffer? = source?.buffer
        val responseBodyString: String? = buffer?.clone()?.readString(Charset.forName("UTF-8"))

        return toErrorResponseString(responseBodyString)
    }

    private fun toErrorResponseString(responseBodyString: String?): String {
        val errorRes = Gson().fromJson(
            responseBodyString, ErrorResponse::class.java
        )
        val errorMsg = "error_msg_unable_to_connect_the_server"
        val errResp = ErrorResponse(
            ErrorData(errorRes.error?.code, errorMsg, errorRes.error?.type),
            ErrorData(errorRes.info?.code, errorMsg, errorRes.info?.type)
        )

        return Gson().toJson(errResp)
    }

}