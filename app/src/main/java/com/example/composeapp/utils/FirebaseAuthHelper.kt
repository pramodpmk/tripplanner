package com.example.composeapp.utils

import com.example.composeapp.data.remote.DataState
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FirebaseAuthHelper @Inject constructor() {

    private var auth: FirebaseAuth? = null

    init {
        auth = Firebase.auth
    }

    /**
     * Authenticate currently logged in user
     */
    suspend fun authenticateCurrentUser(): FirebaseUser? {
        val currentUser = auth?.currentUser
        println("authenticateCurrentUser currentUser -> $currentUser")
        return currentUser
    }

    /**
     * Signup new user
     */
    suspend fun signupNewUser(
        email: String,
        password: String
    ): Flow<DataState<AuthResult>> {
        return callbackFlow {
            auth?.createUserWithEmailAndPassword(
                email, password
            )?.addOnSuccessListener {
                println("signupNewUser authResult -> $it")
                trySend(
                    DataState.Success(it)
                )

            }?.addOnFailureListener {
                it.printStackTrace()
                trySend(
                    DataState.Error(
                        true,
                        null,
                        null,
                        null,
                        null
                    )
                )
            }
            awaitClose {  }
        }
    }

    /**
     * Login already registered users
     */
    suspend fun loginUser(
        email: String,
        password: String
    ): Flow<DataState<AuthResult>> {
        return callbackFlow {
            auth?.signInWithEmailAndPassword(
                email, password
            )?.addOnSuccessListener {
                println("loginUser addOnSuccessListener -> $it")
                trySend(
                    DataState.Success(it)
                )
            }?.addOnFailureListener {
                println("loginUser addOnFailureListener -> $it")
                trySend(
                    DataState.Error(
                        true,
                        null,
                        null,
                        null,
                        null
                    )
                )
            }?.addOnCompleteListener {
                println("loginUser addOnCompleteListener -> $it")
            }
            awaitClose {
                // Clear listeners to avoid leak
            }
        }
    }

    /**
     * Re Authenticate already registered users
     */
    suspend fun reAuthenticateUser(
        email: String,
        password: String
    ): Flow<DataState<Boolean>> {
        return callbackFlow {
            val user = auth?.currentUser
            val credentials = EmailAuthProvider.getCredential(
                email, password
            )
            user?.let { currentUser ->
                currentUser.reauthenticate(credentials).addOnCompleteListener {
                    if (it.isSuccessful) {
                        println("loginUser addOnSuccessListener -> $it")
                        trySend(
                            DataState.Success(true)
                        )
                    } else {
                        println("loginUser addOnFailureListener -> $it")
                        trySend(
                            DataState.Error(
                                true,
                                null,
                                null,
                                null,
                                null
                            )
                        )
                    }
                }
            }

            awaitClose {
                // Clear listeners to avoid leak
            }
        }
    }

    /**
     * Send reset password email
     */
    suspend fun resetPassword(
        email: String
    ): Flow<DataState<String>> {
        return callbackFlow {
            auth?.sendPasswordResetEmail(
                email
            )?.addOnCompleteListener {
                LoggerUtils.traceLog("sendPasswordResetEmail addOnCompleteListener -> $it")
                if (it.isSuccessful) {
                    trySend(
                        DataState.Success("Password reset email sent")
                    )
                } else {
                    trySend(
                        DataState.Error(
                            true,
                            null,
                            null,
                            null,
                            "Password reset email not sent"
                        )
                    )
                }
            }
            awaitClose {
                // Clear listeners to avoid leak
            }
        }
    }

    /**
     * Send reset password email
     */
    suspend fun changePassword(
        password: String
    ): Flow<DataState<String>> {
        LoggerUtils.traceLog("updatePassword -> Entry $password")
        return callbackFlow {
            LoggerUtils.traceLog("updatePassword -> call update password $password")
            auth?.currentUser?.updatePassword(
                password
            )?.addOnCompleteListener {
                LoggerUtils.traceLog("updatePassword addOnCompleteListener -> ${it.exception}")
                if (it.isSuccessful) {
                    trySend(
                        DataState.Success("Password changed")
                    )
                } else {
                    val errorMessage = it.exception?.message
                    trySend(
                        DataState.Error(
                            true,
                            null,
                            null,
                            null,
                            errorMessage ?: "Password change fail"
                        )
                    )
                }
            }
            awaitClose {
                // Clear listeners to avoid leak
            }
        }
    }

    /**
     * Logout already registered users
     */
    suspend fun logoutUser(
        authId: String
    ): Flow<DataState<Boolean>> {
        return callbackFlow {
            auth?.signOut()
            trySend(
                DataState.Success(true)
            )
            awaitClose {
                // Clear listeners to avoid leak
            }
        }
    }

    /**
     * Logout already registered users
     */
    suspend fun deleteUser(
        authId: String
    ): Flow<DataState<Boolean>> {
        return callbackFlow {
            val user = auth?.currentUser!!
            user.delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        trySend(
                            DataState.Success(true)
                        )
                    } else {
                        trySend(
                            DataState.Error(
                                false,
                                1,
                                null,
                                null,
                                "User not deleted"
                            )
                        )
                    }
                }
            awaitClose {
                // Clear listeners to avoid leak
            }
        }
    }
}