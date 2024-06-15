package com.example.composeapp.ui.presentation.profile.domain

import com.example.composeapp.data.remote.DataState
import com.example.composeapp.utils.FirebaseAuthHelper
import com.example.composeapp.utils.LoggerUtils
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class ReAuthenticateUseCase @Inject constructor(
    val firebaseAuthHelper: FirebaseAuthHelper
) {

    operator fun invoke(
        email: String,
        password: String
    ): Flow<DataState<Boolean>> {
        return callbackFlow {
            // Firebase auth with email/password.
            firebaseAuthHelper.reAuthenticateUser(
                email, password
            ).collectLatest {
                LoggerUtils.traceLog("ReAuthenticateUseCase collectLatest")
                when(it) {
                    is DataState.Success -> {
                        trySend(
                            DataState.Success(it.data)
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
}
