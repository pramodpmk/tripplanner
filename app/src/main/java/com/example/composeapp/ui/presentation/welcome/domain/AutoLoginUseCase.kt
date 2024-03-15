package com.example.composeapp.ui.presentation.welcome.domain

import com.example.composeapp.ComposeApp
import com.example.composeapp.data.login.ChatSettingsRepository
import com.example.composeapp.data.remote.DataState
import com.example.composeapp.utils.FireStoreDataHelper
import com.example.composeapp.utils.FirebaseAuthHelper
import com.example.composeapp.utils.LoggerUtils
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AutoLoginUseCase @Inject constructor(
    val firebaseAuthHelper: FirebaseAuthHelper,
    val fireStoreDataHelper: FireStoreDataHelper,
    val loginRepository: ChatSettingsRepository
) {

    operator fun invoke(): Flow<DataState<FirebaseUser>> {
        return callbackFlow {
            delay(1000)
            firebaseAuthHelper.authenticateCurrentUser()?.let { user ->
                LoggerUtils.traceLog("AutoLoginUseCase uid = ${user.uid}")
                ComposeApp.instance?.userId = user.uid
                loadChatGptSettings(
                    success = {
                        trySend(DataState.Success(user))
                    },
                    fail = {
                        trySend(DataState.Success(user))
                    }
                )
                //trySend(DataState.Success(user))
            } ?: kotlin.run {
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

    /**
     * Load the chat gpt settings
     */
    private suspend fun loadChatGptSettings(
        success: () -> Unit,
        fail: () -> Unit
    ) {
        fireStoreDataHelper.getChatGptSettings().collectLatest { item ->
            when(item) {
                is DataState.Success -> {
                    loginRepository.saveGptSettings(
                        token = item.data.api_key,
                        systemMessage = item.data.system_message,
                        tokenLimit = item.data.token_limit
                    )
                    success.invoke()
                }
                is DataState.Error -> {
                    fail.invoke()
                }
            }
        }
    }

}