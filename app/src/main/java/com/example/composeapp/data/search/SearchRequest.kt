package com.example.composeapp.data.search

data class SearchRequest(
    val model: String,
    val messages: List<ChatMessage>,
    val temperature: Int = 1,
    val max_tokens: Int = 1100,
    val top_p: Int = 1
)

data class ChatMessage(
    val role: String,
    val content: String
)