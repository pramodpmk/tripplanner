package com.example.composeapp.ui.presentation.profile.domain

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

class DeleteUserUseCase @Inject constructor(
    val firebaseAuthHelper: FirebaseAuthHelper,
    val fireStoreDataHelper: FireStoreDataHelper
) {

    operator fun invoke(
        userDetails: UserDetails
    ): Flow<DataState<Boolean>> {
        return callbackFlow {
            val userId = ComposeApp.instance?.userId
            LoggerUtils.traceLog("UserDetailsUseCase userId = $userId")
            if (!userId.isNullOrEmpty()) {
                firebaseAuthHelper.deleteUser(userId).collectLatest {
                    when (it) {
                        is DataState.Success -> {
                            println("DeleteUserUseCase Success location ${it.data}")
                            userDetails.email = "deleted-${userDetails.email}"
                            updateUserDetails(
                                userDetails = userDetails,
                                userId = userId,
                                successCallBack = {
                                    trySend(DataState.Success(true))
                                },
                                failCallBack = {
                                    trySend(DataState.Success(true))
                                }
                            )
                        }

                        is DataState.Error -> {
                            println("DeleteUserUseCase error")
                            trySend(it)
                        }
                    }
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

    /**
     * Update user details in table as deleted
     */
    private suspend fun updateUserDetails(
        userDetails: UserDetails,
        userId: String,
        successCallBack: () -> Unit,
        failCallBack: () -> Unit
    ) {
        fireStoreDataHelper.updateUserDetails(
            userId, userDetails
        ).collectLatest {
            when (it) {
                is DataState.Success -> {
                    println("UserDetailsUseCase Success location ${it.data}")
                    successCallBack.invoke()
                }

                is DataState.Error -> {
                    println("UserDetailsUseCase error")
                    failCallBack.invoke()
                }
            }
        }
    }

}
