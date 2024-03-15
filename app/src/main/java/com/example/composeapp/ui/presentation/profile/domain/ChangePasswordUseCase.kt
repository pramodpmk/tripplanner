package com.example.composeapp.ui.presentation.profile.domain

import com.example.composeapp.data.remote.DataState
import com.example.composeapp.utils.FirebaseAuthHelper
import com.example.composeapp.utils.LoggerUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    val firebaseAuthHelper: FirebaseAuthHelper
) {

    suspend operator fun invoke(password: String): Flow<DataState<Boolean>> {
        return callbackFlow {
            firebaseAuthHelper.changePassword(password).collectLatest { state ->
                when(state) {
                    is DataState.Success -> {
                        LoggerUtils.traceLog("changePassword -> Success")
                        trySend(
                            DataState.Success(true)
                        )
                    }
                    is DataState.Error -> {
                        LoggerUtils.traceLog("changePassword -> Error")
                        trySend(
                            DataState.Error(
                                false, 1,
                                null, null, state.message
                            )
                        )
                    }
                    else -> {
                        // Do nothing
                    }
                }
            }
        }
    }
}