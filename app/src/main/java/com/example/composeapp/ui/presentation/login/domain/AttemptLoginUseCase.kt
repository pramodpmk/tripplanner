package com.example.composeapp.ui.presentation.login.domain

import com.example.composeapp.ComposeApp
import com.example.composeapp.data.login.LoginModel
import com.example.composeapp.data.login.ChatSettingsRepository
import com.example.composeapp.data.remote.DataState
import com.example.composeapp.utils.FireStoreDataHelper
import com.example.composeapp.utils.FirebaseAuthHelper
import com.example.composeapp.utils.LoggerUtils
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class AttemptLoginUseCase @Inject constructor(
    val firebaseAuthHelper: FirebaseAuthHelper,
    val fireStoreDataHelper: FireStoreDataHelper,
    val loginRepository: ChatSettingsRepository
) {

    operator fun invoke(
        email: String,
        password: String
    ): Flow<DataState<LoginModel>> {
        return callbackFlow {
            // Firebase auth with email/password.
            firebaseAuthHelper.loginUser(
                email, password
            ).collectLatest {
                LoggerUtils.traceLog("AttemptLoginUseCase collectLatest")
                when(it) {
                    is DataState.Success -> {
                        loadChatGptSettings(
                            success = {

                            },
                            fail = {

                            }
                        )
                        trySend(
                            it.data.user?.uid?.let { userId ->
                                ComposeApp.instance?.userId = userId
                                DataState.Success(
                                    LoginModel(userId)
                                )
                            } ?: run {
                                DataState.Error(
                                    false,
                                    1,
                                    null,
                                    null,
                                    message = "Login Fail"
                                )
                            }
                        )
                    }
                    else -> {
                        trySend(
                            DataState.Error(
                                false,
                                1,
                                null,
                                null,
                                message = "Login Fail"
                            )
                        )
                    }
                }
            }
            awaitClose {
                // Do nothing
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