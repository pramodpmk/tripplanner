package com.example.composeapp.ui.presentation.detail.domain

import com.example.composeapp.data.detail.DetailModel
import com.example.composeapp.data.remote.DataState
import com.example.composeapp.utils.FireStoreDataHelper
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class LoadTripDetailsUseCase @Inject constructor(
    val fireStoreDataHelper: FireStoreDataHelper
) {

    operator fun invoke(id: String): Flow<DataState<DetailModel>> {
        return callbackFlow {
            var tripId = id.replace("{", "")
            tripId = tripId.replace("}", "")
            fireStoreDataHelper.getTripDetails(
                tripId
            ).collectLatest { data ->
                when(data) {
                    is DataState.Success -> {
                        trySend(DataState.Success(data.data))
                    }
                    is DataState.Error -> {
                        trySend(
                            DataState.Error(
                                false,
                                1,
                                null,
                                null,
                                ""
                            )
                        )
                    }
                }
            }

            awaitClose {

            }
        }
    }
}
