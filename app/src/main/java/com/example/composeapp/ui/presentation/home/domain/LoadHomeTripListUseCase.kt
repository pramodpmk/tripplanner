package com.example.composeapp.ui.presentation.home.domain

import com.example.composeapp.data.home.TripItemModel
import com.example.composeapp.data.remote.DataState
import com.example.composeapp.utils.FireStoreDataHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class LoadHomeTripListUseCase @Inject constructor(
    val fireStoreDataHelper: FireStoreDataHelper
) {

    operator fun invoke(
        typeOfList: Int
    ): Flow<DataState<MutableList<TripItemModel>>> {
        return callbackFlow {
            getTripList(
                tripType = typeOfList,
                success = { list ->
                    trySend(DataState.Success(list))
                },
                fail = {
                    trySend(
                        DataState.Error(
                            false,
                            1, null, null, ""
                        )
                    )
                }
            )
        }
    }

    private suspend fun getTripList(
        tripType: Int,
        success: (MutableList<TripItemModel>) -> Unit,
        fail: () -> Unit
    ) {
        fireStoreDataHelper.getTripItems(
            tripType
        ).distinctUntilChanged().collectLatest { result ->
            when (result) {
                is DataState.Success -> {
                    success.invoke(result.data)
                }

                is DataState.Error -> {
                    fail.invoke()
                }
            }
        }
    }

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