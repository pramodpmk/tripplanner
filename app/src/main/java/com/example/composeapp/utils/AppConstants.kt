package com.example.composeapp.utils

object AppConstants {

    object TripType {
        const val SPIRITUAL = 1
        const val HONEY_MOON = 2
        const val GROUP = 3
        const val SOLO = 4
        const val ROAD_TRIP = 5
        const val BIKE_TRIP = 6
    }

    object TripDescription {
        const val SPIRITUAL = "Spiritual travel"
        const val HONEY_MOON = "Honey moon"
        const val GROUP = "Group trip"
        const val SOLO = "Solo trip"
        const val ROAD_TRIP = "Road trip"
        const val BIKE_TRIP = "Bike trip"
    }

    object SourcePage {
        const val ParamType = "sourcePage"
        const val SRC_SEARCH = "search_page"
        const val SRC_LISTING = "listing_page"
    }

    object EditField {
        const val NAME = "NAME"
        const val EMAIL = "EMAIL"
        const val LOCATION = "LOCATION"
        const val PASSWORD = "PASSWORD"
    }

    object SearchParam {
        const val GPT_MODEL = "gpt-3.5-turbo"
    }
}
