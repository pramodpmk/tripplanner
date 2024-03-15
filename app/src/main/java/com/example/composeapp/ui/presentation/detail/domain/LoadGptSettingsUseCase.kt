package com.example.composeapp.ui.presentation.detail.domain

import com.example.composeapp.data.UiState
import com.example.composeapp.data.detail.DetailModel
import com.example.composeapp.data.login.ChatGptSettings
import com.example.composeapp.data.login.ChatSettingsRepository
import com.example.composeapp.data.remote.DataState
import com.example.composeapp.utils.FireStoreDataHelper
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadGptSettingsUseCase @Inject constructor(
    val settingsRepository: ChatSettingsRepository,
    val fireStoreDataHelper: FireStoreDataHelper
) {

    operator fun invoke(): Flow<DataState<ChatGptSettings>> {
        return callbackFlow {
            settingsRepository.getGptSettings()?.let { result ->
                trySend(DataState.Success(result))
            } ?: kotlin.run {
                fireStoreDataHelper.getChatGptSettings().collectLatest { data ->
                    when(data) {
                        is DataState.Success -> {
                            settingsRepository.saveGptSettings(
                                token = data.data.api_key,
                                systemMessage = data.data.system_message,
                                tokenLimit = data.data.token_limit
                            )
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
            }

            awaitClose {

            }
        }
    }
}
