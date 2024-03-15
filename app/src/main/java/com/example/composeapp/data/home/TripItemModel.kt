package com.example.composeapp.data.home

data class TripItemModel(
    var tripTitle: String,
    var description: String,
    var imageUrl: String,
    var numOfDays: Int,
    var tripId: String = "",
    var tripCategory: String = ""
)