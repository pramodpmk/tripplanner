package com.example.composeapp.data.home

import com.example.composeapp.data.detail.DetailModel

data class TripItemModel(
    var tripTitle: String,
    var description: String,
    var imageUrl: String,
    var numOfDays: Int,
    var tripId: String = "",
    var tripCategory: String = ""
) {

    companion object {

        fun fromDetailModel(item: DetailModel): TripItemModel {
            val image = if (item.images.isNotEmpty()) {
                item.images[0]
            } else {
                ""
            }
            return TripItemModel(
                tripTitle = item.title,
                description = item.description,
                imageUrl = image,
                numOfDays = item.days.size ?: 0,
                tripId = item.id,
                tripCategory = ""
            )
        }
    }
}