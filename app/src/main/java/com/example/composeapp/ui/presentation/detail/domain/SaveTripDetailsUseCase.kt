package com.example.composeapp.ui.presentation.detail.domain

import com.example.composeapp.data.detail.DetailDto
import com.example.composeapp.data.detail.DetailModel
import com.example.composeapp.data.remote.DataState
import com.example.composeapp.utils.FireStoreDataHelper
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class SaveTripDetailsUseCase @Inject constructor(
    val fireStoreDataHelper: FireStoreDataHelper
) {

    operator fun invoke(
        detailModel: DetailModel,
        tripType: String
    ): Flow<DataState<Boolean>> {
        return callbackFlow {
            val id = "${detailModel.id}+${System.currentTimeMillis()}"
            detailModel.id = id
            val detailDto = DetailDto.fromModel(detailModel, tripType)
            fireStoreDataHelper.saveTripDetails(detailDto).collectLatest { data ->
                when(data) {
                    is DataState.Success -> {
                        trySend(DataState.Success(true))
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
