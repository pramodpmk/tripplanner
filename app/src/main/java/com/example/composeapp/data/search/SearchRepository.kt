package com.example.composeapp.data.search

import com.example.composeapp.data.remote.BaseRepository
import com.example.composeapp.data.remote.ChatGptService
import com.example.composeapp.utils.AppConstants
import com.example.composeapp.utils.InternetUtils
import javax.inject.Inject

class SearchRepository @Inject constructor(
    val internetUtils: InternetUtils,
    val searchService: ChatGptService
) : BaseRepository() {

    override fun getNetworkUtils() = internetUtils

    /**
     * Search for a trip plan
     */
    suspend fun search(
        query: String,
        systemMessage: String = "",
        tokenLimit: String = ""
    ) = safeApiCall {
        val maxTokens: Int = tokenLimit.toIntOrNull()?.let {
            it
        } ?: kotlin.run {
            2000
        }
        val request = SearchRequest(
            model = AppConstants.SearchParam.GPT_MODEL,
            messages = listOf(
                ChatMessage("system", systemMessage),
                ChatMessage("user", query)
            ),
            max_tokens = maxTokens
        )
        searchService.search(
            request = request
        )
    }
}