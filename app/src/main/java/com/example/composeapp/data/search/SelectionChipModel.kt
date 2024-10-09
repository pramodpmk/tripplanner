package com.example.composeapp.data.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class SelectionChipModel(
    val title: String,
    val id: String = ""
) {

    var isSelected: MutableState<Boolean> = mutableStateOf(value = false)
}
