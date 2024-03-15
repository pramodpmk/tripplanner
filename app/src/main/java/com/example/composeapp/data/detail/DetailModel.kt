package com.example.composeapp.data.detail

data class DetailModel(
    var id: String,
    var title: String,
    var location: String,
    var dayDesc: String,
    var description: String,
    var tips: List<String>,
    var days: List<DaysOfTrip>,
    var images: List<String>
) {

    data class DaysOfTrip(
        var dayName: String,
        var morning: List<TimeOfDay>,
        var afternoon: List<TimeOfDay>,
        var evening: List<TimeOfDay>,
        var sortOrder: Int
    )

    data class TimeOfDay(
        var description: String,
        var sortId: Int
    )
}

