package com.example.composeapp.ui.presentation.register.domain

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

class RegisterUserUseCase @Inject constructor(
    val firebaseAuthHelper: FirebaseAuthHelper,
    val fireStoreDataHelper: FireStoreDataHelper
) {

    operator fun invoke(
        name: String,
        email: String,
        password: String,
        location: String
    ): Flow<DataState<LoginModel>> {
        return callbackFlow {
            // Firebase auth with email/password.
            // Store user details after registration in cloud firestore
            firebaseAuthHelper.signupNewUser(
                email, password
            ).collectLatest {
                when (it) {
                    is DataState.Success -> {
                        println("RegisterUserUseCase Success")
                        it.data.user?.uid?.let { userId ->
                            saveUserDetails(name, email, location, userId).collectLatest { id ->
                                id?.let { regId ->
                                    ComposeApp.instance?.userId = userId
                                    trySend(
                                        DataState.Success(
                                            LoginModel(regId)
                                        )
                                    )
                                } ?: kotlin.run {
                                    trySend(
                                        DataState.Error(
                                            false,
                                            1,
                                            null,
                                            null,
                                            "Registration not success"
                                        )
                                    )
                                }
                            }
                        } ?: kotlin.run {
                            trySend(
                                DataState.Error(
                                    false,
                                    1,
                                    null,
                                    null,
                                    "Registration not success"
                                )
                            )
                        }
                    }

                    else -> {
                        println("RegisterUserUseCase Success")
                        trySend(
                            DataState.Error(
                                false,
                                1,
                                null,
                                null,
                                "Registration not success"
                            )
                        )
                    }
                }
            }
            awaitClose {

            }
        }
    }

    private suspend fun saveUserDetails(
        name: String,
        email: String,
        location: String,
        userId: String
    ): Flow<String?> {
        return callbackFlow {
            val item = UserDetails()
            item.name = name
            item.email = email
            item.location = location
            item.authId = userId
            fireStoreDataHelper.saveUserDetails(item).collectLatest {
                when (it) {
                    is DataState.Success -> {
                        LoggerUtils.traceLog("saveUserDetails success")
                        trySend(userId)
                    }

                    else -> {
                        LoggerUtils.traceLog("saveUserDetails fail")
                        trySend(null)
                    }
                }
            }
            awaitClose { }
        }
    }
}
