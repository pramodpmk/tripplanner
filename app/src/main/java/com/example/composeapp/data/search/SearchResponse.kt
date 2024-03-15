package com.example.composeapp.data.search

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("id") var id: String? = null,
    @SerializedName("object") var objectParam: String? = null,
    @SerializedName("created") var created: Int? = null,
    @SerializedName("model") var model: String? = null,
    @SerializedName("choices") var choices: ArrayList<SearchChoices> = arrayListOf(),
    @SerializedName("usage") var usage: SearchUsage? = SearchUsage(),
    @SerializedName("system_fingerprint") var systemFingerprint: String? = null
) {

    data class SearchUsage(
        @SerializedName("prompt_tokens") var promptTokens: Int? = null,
        @SerializedName("completion_tokens") var completionTokens: Int? = null,
        @SerializedName("total_tokens") var totalTokens: Int? = null
    )

    data class SearchMessage(
        @SerializedName("role") var role: String? = null,
        @SerializedName("content") var content: String? = null
    )

    data class SearchChoices(
        @SerializedName("index") var index: Int? = null,
        @SerializedName("message") var message: SearchMessage? = SearchMessage(),
        @SerializedName("logprobs") var logprobs: String? = null,
        @SerializedName("finish_reason") var finishReason: String? = null
    )

}
