package com.example.composeapp.data.login

class ChatGptSettings() {

    constructor(
        _apiKey: String,
        _systemMessage: String
    ) : this() {
        api_key = _apiKey
        system_message = _systemMessage
    }

    lateinit var api_key: String
    lateinit var system_message: String
    lateinit var token_limit: String
}
