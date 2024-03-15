package com.example.composeapp.ui.presentation.home.domain

import com.example.composeapp.data.home.TripItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

const val LIST_TYPE_HONEYMOON = 1
const val LIST_TYPE_GROUP = 2
const val LIST_TYPE_SOLO = 3
const val LIST_TYPE_ROAD_TRIP = 4
const val LIST_TYPE_SPIRITUAL = 5
const val LIST_TYPE_BIKE_TRIP = 6

class LoadHomeTripListUseCase @Inject constructor() {

    operator fun invoke(
        typeOfList: Int
    ): Flow<MutableList<TripItemModel>> {
        val itemList = when(typeOfList) {
            LIST_TYPE_HONEYMOON -> {
                getHoneymoonList()
            }
            LIST_TYPE_GROUP -> {
                getGroupTravelList()
            }
            LIST_TYPE_SOLO -> {
                getSoloTravelList()
            }
            LIST_TYPE_ROAD_TRIP -> {
                getRoadTripList()
            }
            LIST_TYPE_BIKE_TRIP -> {
                getBikeTripList()
            }
            LIST_TYPE_SPIRITUAL -> {
                getSpiritualList()
            }
            else -> {
                mutableListOf()
            }
        }
        return flow {
            emit(itemList)
        }
    }

    private fun getHoneymoonList() = mutableListOf(
        TripItemModel(
            "Honeymoon 1",
            "View honeymoon one package",
            "https://tripai.s3.eu-north-1.amazonaws.com/places/toy-train1.jpg?response-content-disposition=inline&X-Amz-Security-Token=IQoJb3JpZ2luX2VjEHsaDmFwLXNvdXRoZWFzdC0xIkcwRQIhALDjatSy8WMkCttSb408U3bNAE1QVLrEh3gbKtkIr6T8AiAR632LdD%2FlY27Gdk8HwNj7YpjqljbeuhxzeRSPUTB6SyrkAghkEAAaDDk3NTA0OTk1NTI4NCIMCuYcRrQA%2Fr9NDWhGKsEC98m3ss58urGztHvrx7bDe2pn59d54HT%2FfqfqbgdDoAAYj2Oj4xa03BT1F5TQRTJwMTSLapO%2FU4kcuFV5UCmeMXSNyRRZ4HfzLoi23hlCZop5x86l4p3BJFoWsXQcStP5xWObMYm6LsWpiRz%2BROQHMzWtbtcQmvEGn%2BLELWjqt7IpquDLVFFk6CeTiu5znwdHB8r1JXeE2LFHuByAawwTaTaxzigrNqoW%2F4w6uVlFasGSbUjz9wrlcgonImg7%2Fjji2wp4UnvaRxiH%2BfLS%2BNnmvA5HywRmJUIhddrTLR5ZCZwAqH78Tc2lM3zfdUojx8%2BvsvpeIgEzrkoao2TyagnEH91IF6Eo8kUlWe0Y8gVIvm23Ospw2vDnrhIyE%2FSopuj7dF3ZZWa7O80PwkwVsz0cLghXieV1XEH8PXPYe%2Bo%2ByoNCMLSp3q4GOrMCP7PZ4OxIMPYuj3PextAojbMDkb%2B%2FugIr%2FflQ8NZgVbgw7ddPb9OIAwHBnkDvCbQZxoypD0Kk6oeUsWS8MSdg52Ao1ZkQydzGZ7Wq%2FtQGGGGsqYf3GuML7BJ49B5WDLoJe6VzF9xbdXpbiIIW9zQ1QEXN9mZKdFxEgQmpi%2FzUkbwJ3tgTMDr%2B5zNWbAbFg3fe0Iw7cBhBn0e4y9f8xpt6jQybdPh7mJIrWKGjSdtQ7VzCxS%2BAQaDSi0Jl%2BVRuHiHcbSC%2F5gxQV8SwDK%2BrgNwrk%2Fpmim9zhANzv8YO0Cqhqj9MwruCP1VFOmt6p%2FvLRzNq5NPmDWoKfcGz0Ti2UTpDx39AjOS8jYpBMtZow8kSattbDXvnpYEC2jX29O3x%2FliXc8HYxPCGxNZqX2DOVqwWA1Lhew%3D%3D&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20240222T193555Z&X-Amz-SignedHeaders=host&X-Amz-Expires=300&X-Amz-Credential=ASIA6GBMBS7KA7NKY5TO%2F20240222%2Feu-north-1%2Fs3%2Faws4_request&X-Amz-Signature=8bf85e8d5edb13336e6ffb1ee7cd125f52714ee558d05c7813b12845872fa27d",
            2
        ),
        TripItemModel(
            "Honeymoon 2",
            "View honeymoon two package",
            "https://res.cloudinary.com/dddd9bezd/image/upload/v1675228784/AiAgents/chatbot-100x100_a6ur0q.jpg",
            2
        ),
        TripItemModel(
            "Honeymoon 3",
            "View honeymoon three package",
            "https://res.cloudinary.com/dddd9bezd/image/upload/v1675228784/AiAgents/chatbot-100x100_a6ur0q.jpg",
            2
        )
    )

    private fun getGroupTravelList() = mutableListOf(
        TripItemModel(
            "Group travel 1",
            "View group travel 1 package",
            "https://res.cloudinary.com/dddd9bezd/image/upload/v1675228784/AiAgents/chatbot-100x100_a6ur0q.jpg",
            2
        ),
        TripItemModel(
            "Group travel 2",
            "View group travel 2 package",
            "https://res.cloudinary.com/dddd9bezd/image/upload/v1675228784/AiAgents/chatbot-100x100_a6ur0q.jpg",
            2
        ),
        TripItemModel(
            "Group travel 3",
            "View group travel 3 package",
            "https://res.cloudinary.com/dddd9bezd/image/upload/v1675228784/AiAgents/chatbot-100x100_a6ur0q.jpg",
            2
        ),
        TripItemModel(
            "Group travel 4",
            "View group travel 4 package",
            "https://res.cloudinary.com/dddd9bezd/image/upload/v1675228784/AiAgents/chatbot-100x100_a6ur0q.jpg",
            2
        ),
        TripItemModel(
            "Group travel 5",
            "View group travel 5 package",
            "https://res.cloudinary.com/dddd9bezd/image/upload/v1675228784/AiAgents/chatbot-100x100_a6ur0q.jpg",
            2
        ),
        TripItemModel(
            "Group travel 6",
            "View group travel 6 package",
            "https://res.cloudinary.com/dddd9bezd/image/upload/v1675228784/AiAgents/chatbot-100x100_a6ur0q.jpg",
            2
        ),
        TripItemModel(
            "Group travel 7",
            "View group travel 7 package",
            "https://res.cloudinary.com/dddd9bezd/image/upload/v1675228784/AiAgents/chatbot-100x100_a6ur0q.jpg",
            2
        ),
        TripItemModel(
            "Group travel 8",
            "View group travel 8 package",
            "https://res.cloudinary.com/dddd9bezd/image/upload/v1675228784/AiAgents/chatbot-100x100_a6ur0q.jpg",
            2
        ),
        TripItemModel(
            "Group travel 9",
            "View group travel 9 package",
            "https://res.cloudinary.com/dddd9bezd/image/upload/v1675228784/AiAgents/chatbot-100x100_a6ur0q.jpg",
            2
        ),
        TripItemModel(
            "Group travel 10",
            "View group travel 10 package",
            "https://res.cloudinary.com/dddd9bezd/image/upload/v1675228784/AiAgents/chatbot-100x100_a6ur0q.jpg",
            2
        ),
        TripItemModel(
            "Group travel 11",
            "View group travel 11 package",
            "https://res.cloudinary.com/dddd9bezd/image/upload/v1675228784/AiAgents/chatbot-100x100_a6ur0q.jpg",
            2
        ),
        TripItemModel(
            "Group travel 12",
            "View group travel 12 package",
            "https://res.cloudinary.com/dddd9bezd/image/upload/v1675228784/AiAgents/chatbot-100x100_a6ur0q.jpg",
            2
        )
    )

    private fun getSoloTravelList() = mutableListOf(
        TripItemModel(
            "Solo 1",
            "View solo one package",
            "http://www.androidtutorialshub.com/wp-content/uploads/2016/01/banner_glide.png",
            2
        ),
        TripItemModel(
            "Solo 2",
            "View solo two package",
            "http://www.androidtutorialshub.com/wp-content/uploads/2016/01/banner_glide.png",
            2
        ),
        TripItemModel(
            "Solo 3",
            "View solo three package",
            "http://www.androidtutorialshub.com/wp-content/uploads/2016/01/banner_glide.png",
            2
        )
    )

    private fun getSpiritualList() = mutableListOf(
        TripItemModel(
            "Spiritual 1",
            "View spiritual one package",
            "http://www.androidtutorialshub.com/wp-content/uploads/2016/01/banner_glide.png",
            2
        ),
        TripItemModel(
            "Spiritual 2",
            "View spiritual two package",
            "http://www.androidtutorialshub.com/wp-content/uploads/2016/01/banner_glide.png",
            2
        ),
        TripItemModel(
            "Spiritual 3",
            "View spiritual three package",
            "http://www.androidtutorialshub.com/wp-content/uploads/2016/01/banner_glide.png",
            2
        )
    )

    private fun getRoadTripList() = mutableListOf(
        TripItemModel(
            "Road trip 1",
            "View Road trip one package",
            "https://res.cloudinary.com/dddd9bezd/image/upload/v1675228784/AiAgents/chatbot-100x100_a6ur0q.jpg",
            2
        ),
        TripItemModel(
            "Road trip 2",
            "View Road trip two package",
            "https://res.cloudinary.com/dddd9bezd/image/upload/v1675228784/AiAgents/chatbot-100x100_a6ur0q.jpg",
            2
        ),
        TripItemModel(
            "Road trip 3",
            "View Road trip three package",
            "https://res.cloudinary.com/dddd9bezd/image/upload/v1675228784/AiAgents/chatbot-100x100_a6ur0q.jpg",
            2
        )
    )

    private fun getBikeTripList() = mutableListOf(
        TripItemModel(
            "Bike trip 1",
            "View Bike trip one package",
            "https://res.cloudinary.com/dddd9bezd/image/upload/v1675228784/AiAgents/chatbot-100x100_a6ur0q.jpg",
            2
        ),
        TripItemModel(
            "Bike trip 2",
            "View Bike trip two package",
            "https://res.cloudinary.com/dddd9bezd/image/upload/v1675228784/AiAgents/chatbot-100x100_a6ur0q.jpg",
            2
        ),
        TripItemModel(
            "Bike trip 3",
            "View Bike trip three package",
            "https://res.cloudinary.com/dddd9bezd/image/upload/v1675228784/AiAgents/chatbot-100x100_a6ur0q.jpg",
            2
        )
    )
}