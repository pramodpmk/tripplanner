package com.example.composeapp.data.detail

data class DetailModel(
    var id: String = "",
    var title: String = "",
    var location: String = "",
    var dayDesc: String = "",
    var description: String = "",
    var tips: List<String> = arrayListOf(),
    var days: List<DaysOfTrip> = arrayListOf(),
    var images: List<String> = arrayListOf()
) {

    data class DaysOfTrip(
        var dayName: String = "",
        var morning: List<TimeOfDay> = arrayListOf(),
        var afternoon: List<TimeOfDay> = arrayListOf(),
        var evening: List<TimeOfDay> = arrayListOf(),
        var sortOrder: Int = 0
    )

    data class TimeOfDay(
        var description: String = "",
        var sortId: Int = 0
    )
}

