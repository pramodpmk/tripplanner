package com.example.composeapp.data.detail

class DetailDto() {
    var id: String = ""
    var title: String = ""
    var location: String = ""
    var dayDesc: String = ""
    var description: String = ""
    var tips: List<String> = emptyList()
    var days: List<DaysOfTrip> = emptyList()
    var images: List<String> = emptyList()
    var tripType: String = ""

    constructor(
        _id: String,
        _title: String,
        _location: String,
        _dayDesc: String,
        _description: String,
        _tips: List<String>,
        _days: List<DaysOfTrip>,
        _images: List<String>,
        _tripType: String
    ) : this() {
        id = _id
        title = _title
        location = _location
        dayDesc = _dayDesc
        description = _description
        tips = _tips
        days = _days
        images = _images
        tripType = _tripType
    }

    class DaysOfTrip() {
        var dayName: String = ""
        var morning: List<TimeOfDay> = emptyList()
        var afternoon: List<TimeOfDay> = emptyList()
        var evening: List<TimeOfDay> = emptyList()
        var sortOrder: Int = 0

        constructor(
            _dayName: String,
            _morning: List<TimeOfDay>,
            _afternoon: List<TimeOfDay>,
            _evening: List<TimeOfDay>,
            _sortOrder: Int
        ) : this() {
            dayName = _dayName
            morning = _morning
            afternoon = _afternoon
            evening = _evening
            sortOrder = _sortOrder

        }
    }

    class TimeOfDay() {
        var description: String = ""
        var sortId: Int = 0

        constructor(
            desc: String,
            sort: Int
        ) : this() {
            description = desc
            sortId = sort
        }
    }

    companion object {

        fun toModel(dto: DetailDto): DetailModel {
            val days = arrayListOf<DetailModel.DaysOfTrip>()
            dto.days.forEach { day ->
                val morning = arrayListOf<DetailModel.TimeOfDay>()
                day.morning.forEach { timeOfDay ->
                    morning.add(
                        DetailModel.TimeOfDay(
                            description = timeOfDay.description,
                            sortId = timeOfDay.sortId
                        )
                    )
                }
                val afternoon = arrayListOf<DetailModel.TimeOfDay>()
                day.afternoon.forEach { timeOfDay ->
                    afternoon.add(
                        DetailModel.TimeOfDay(
                            description = timeOfDay.description,
                            sortId = timeOfDay.sortId
                        )
                    )
                }
                val evening = arrayListOf<DetailModel.TimeOfDay>()
                day.evening.forEach { timeOfDay ->
                    evening.add(
                        DetailModel.TimeOfDay(
                            description = timeOfDay.description,
                            sortId = timeOfDay.sortId
                        )
                    )
                }
                days.add(
                    DetailModel.DaysOfTrip(
                        dayName = day.dayName,
                        morning = morning,
                        afternoon = afternoon,
                        evening = evening,
                        sortOrder = day.sortOrder
                    )
                )
            }
            return DetailModel(
                id = dto.id,
                title = dto.title,
                location = dto.location,
                description = dto.description,
                dayDesc = dto.dayDesc,
                tips = dto.tips,
                days = days,
                images = dto.images
            )
        }

        fun fromModel(model: DetailModel, tripType: String): DetailDto {
            val days = arrayListOf<DaysOfTrip>()
            model.days.forEach { day ->
                val morning = arrayListOf<TimeOfDay>()
                day.morning.forEach { timeOfDay ->
                    morning.add(
                        TimeOfDay(
                            desc = timeOfDay.description,
                            sort = timeOfDay.sortId
                        )
                    )
                }
                val afternoon = arrayListOf<TimeOfDay>()
                day.afternoon.forEach { timeOfDay ->
                    afternoon.add(
                        TimeOfDay(
                            desc = timeOfDay.description,
                            sort = timeOfDay.sortId
                        )
                    )
                }
                val evening = arrayListOf<TimeOfDay>()
                day.evening.forEach { timeOfDay ->
                    evening.add(
                        TimeOfDay(
                            desc = timeOfDay.description,
                            sort = timeOfDay.sortId
                        )
                    )
                }
                days.add(
                    DaysOfTrip(
                        _dayName = day.dayName,
                        _morning = morning,
                        _afternoon = afternoon,
                        _evening = evening,
                        _sortOrder = day.sortOrder
                    )
                )
            }
            return DetailDto(
                _id = model.id,
                _title = model.title,
                _location = model.location,
                _description = model.description,
                _dayDesc = model.dayDesc,
                _tips = model.tips,
                _days = days,
                _images = model.images,
                _tripType = tripType
            )
        }
    }
}