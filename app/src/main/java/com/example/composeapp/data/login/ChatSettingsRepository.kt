package com.example.composeapp.data.login

import com.example.composeapp.data.local.DataStoreHelper
import com.example.composeapp.data.remote.BaseRepository
import com.example.composeapp.utils.InternetUtils
import javax.inject.Inject

class ChatSettingsRepository @Inject constructor(
    val internetUtils: InternetUtils,
    val dataStoreHelper: DataStoreHelper
) : BaseRepository() {

    override fun getNetworkUtils() = internetUtils

    /**
     * Search for a trip plan
     */
    suspend fun saveGptSettings(
        token: String,
        systemMessage: String = "",
        tokenLimit: String = ""
    ) {
        dataStoreHelper.storeGptToken(token)
        dataStoreHelper.storeGptSettingsMessage(systemMessage)
        dataStoreHelper.storeGptTokenLimit(tokenLimit)
    }

    /**
     * Load chat gpt settings from datastore
     */
    suspend fun getGptSettings(): ChatGptSettings? {
        val token = dataStoreHelper.getStoredGptToken()
        val settings = dataStoreHelper.getStoredGptSettingsMessage()
        val tokenLimit = dataStoreHelper.getStoredGptLimit()
        return if (token != "" && settings != "") {
            val gpt = ChatGptSettings(token, settings)
            gpt.token_limit = tokenLimit
            gpt
        } else {
            null
        }
    }
}