package com.example.composeapp.ui.presentation.detail.domain

import com.example.composeapp.data.UiState
import com.example.composeapp.data.detail.DetailModel
import com.example.composeapp.data.login.ChatGptSettings
import com.example.composeapp.data.remote.DataState
import com.example.composeapp.data.search.SearchRepository
import com.google.gson.Gson
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadSearchResultUseCase @Inject constructor(
    val searchRepository: SearchRepository
) {

    operator fun invoke(
        searchKey: String,
        chatGptSettings: ChatGptSettings
    ): Flow<DataState<DetailModel>> {
        return callbackFlow {
            val resp = searchRepository.search(
                searchKey,
                chatGptSettings.system_message
            )
            when(resp) {
                is DataState.Success -> {
                    try {
                        if (resp.data.choices.isNotEmpty()) {
                            val json = resp.data.choices[0].message?.content
                            val data = if (json != null) {
                               Gson().fromJson(
                                    json,
                                    DetailModel::class.java
                                )
                            } else {
                                null
                            }
                            data?.let { item ->
                                trySend(
                                    DataState.Success(item)
                                )
                            } ?: kotlin.run {
                                trySend(getErrrorData())
                            }
                        } else {
                            trySend(getErrrorData())
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        trySend(getErrrorData())
                    }
                }
                is DataState.Error -> {
                    trySend(getErrrorData())
                }
            }

            awaitClose {

            }
        }
    }

    private fun getErrrorData(): DataState.Error {
        return DataState.Error(
            false,
            1,
            null,
            null,
            "Not able to fetch travel plan"

        )
    }

    private fun getSearchResult(): DataState.Success<DetailModel> {
        val morning = arrayListOf(
            DetailModel.TimeOfDay(
                description = "Visit Doddabetta Peak, the highest point in the Nilgiri Mountains, and take in the breathtaking panoramic views of the surrounding valleys and tea estates 1.",
                sortId = 1
            ),
            DetailModel.TimeOfDay(
                description = "Head to the famous tea gardens of Ooty, especially the scenic estates of Glenmorgan or the sprawling tea plantations of Dodabetta Valley. Enjoy a leisurely walk amid the tea bushes, learn about the tea-making process, and don’t forget to sample some freshly brewed tea",
                sortId = 2
            )
        )
        val afternoon = arrayListOf(
            DetailModel.TimeOfDay(
                description = "Make your way to Ooty Lake, a popular tourist attraction in the town. Indulge in a boat ride across the serene lake, surrounded by eucalyptus trees and charming hills",
                sortId = 1
            ),
            DetailModel.TimeOfDay(
                description = "Explore the well-maintained Botanical Gardens, home to an enormous variety of exotic plants, vibrant flowers, and exquisite trees. You can take a relaxing stroll, admire the stunning floral vistas, and capture memorable photographs 1",
                sortId = 2
            )
        )
        val evening = arrayListOf(
            DetailModel.TimeOfDay(
                description = "Don’t miss the vibrant Rose Garden, nestled in the heart of Ooty. Marvel at the numerous varieties of roses in full bloom, breathe in the fragrant air, and spend a peaceful time amid nature’s beauty ",
                sortId = 1
            ),
            DetailModel.TimeOfDay(
                description = "Treat your taste buds at the Ooty Chocolate Factory, known for its delectable homemade chocolates. Witness the chocolate-making process, indulge in some sweet delights, and take home delicious souvenirs",
                sortId = 2
            )
        )
        val tips = arrayListOf<String>(
            "Ooty is a beautiful hill station located in the Nilgiri Hills of Tamil Nadu, India. Here’s a 3-day itinerary that covers some of the best places to visit in Ooty",
            "Ooty is a beautiful hill station located in the Nilgiri Hills of Tamil Nadu, India. Here’s a 3-day itinerary that covers some of the best places to visit in Ooty"
        )
        return DataState.Success(
            DetailModel(
                id = "1234",
                title = "Honeymoon to Ootty",
                location = "Ootty, Tamilnadu, India",
                dayDesc = "3 Day, 4 Nights",
                description = " Ooty is a beautiful hill station located in the Nilgiri Hills of Tamil Nadu, India. Here’s a 3-day itinerary that covers some of the best places to visit in Ooty",
                tips = tips,
                days = arrayListOf(
                    DetailModel.DaysOfTrip(
                        "Day 1",
                        morning = morning,
                        afternoon = afternoon,
                        evening = evening,
                        sortOrder = 1
                    ),
                    DetailModel.DaysOfTrip(
                        "Day 2",
                        morning = morning,
                        afternoon = afternoon,
                        evening = evening,
                        sortOrder = 1
                    ),
                    DetailModel.DaysOfTrip(
                        "Day 3",
                        morning = morning,
                        afternoon = afternoon,
                        evening = evening,
                        sortOrder = 1
                    )
                ),
                images = arrayListOf()
            )
        )
    }
}