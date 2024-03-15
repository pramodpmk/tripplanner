package com.example.composeapp.ui.presentation.home.domain

import com.example.composeapp.ComposeApp
import com.example.composeapp.data.login.LoginModel
import com.example.composeapp.data.remote.DataState
import com.example.composeapp.data.user.UserDetails
import com.example.composeapp.utils.FireStoreDataHelper
import com.example.composeapp.utils.FirebaseAuthHelper
import com.example.composeapp.utils.LoggerUtils
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserDetailsUseCase @Inject constructor(
    val fireStoreDataHelper: FireStoreDataHelper
) {

    operator fun invoke(): Flow<DataState<UserDetails>> {
        return callbackFlow {
            val userId = ComposeApp.instance?.userId
            LoggerUtils.traceLog("UserDetailsUseCase userId = $userId")
            if (!userId.isNullOrEmpty()) {
                fireStoreDataHelper.getUserDetails(userId).collectLatest {
                    val resp = when (it) {
                        is DataState.Success -> {
                            println("UserDetailsUseCase Success location ${it.data.location}")
                            it
                        }

                        is DataState.Error -> {
                            println("UserDetailsUseCase error")
                            it
                        }
                    }
                    trySend(resp)
                }
            } else {
                trySend(DataState.Error(
                    false, null, null, null, ""
                ))
            }
            awaitClose {
                // DO nothing
            }
        }
    }

}
