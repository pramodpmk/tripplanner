package com.example.composeapp.ui.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeapp.ComposeApp
import com.example.composeapp.data.UiState
import com.example.composeapp.data.login.LoginModel
import com.example.composeapp.data.remote.DataState
import com.example.composeapp.data.user.UserDetails
import com.example.composeapp.ui.presentation.home.domain.UserDetailsUseCase
import com.example.composeapp.ui.presentation.profile.domain.ChangePasswordUseCase
import com.example.composeapp.ui.presentation.profile.domain.DeleteUserUseCase
import com.example.composeapp.ui.presentation.profile.domain.LogoutUserUseCase
import com.example.composeapp.ui.presentation.profile.domain.ReAuthenticateUseCase
import com.example.composeapp.ui.presentation.profile.domain.UpdateUserDetailsUseCase
import com.example.composeapp.ui.presentation.welcome.domain.AutoLoginUseCase
import com.example.composeapp.utils.AppConstants
import com.example.composeapp.utils.AppUtils.isValidEmail
import com.example.composeapp.utils.LoggerUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val changePasswordUseCase: ChangePasswordUseCase,
    val userDetailsUseCase: UserDetailsUseCase,
    val updateUserDetailsUseCase: UpdateUserDetailsUseCase,
    val logoutUserUseCase: LogoutUserUseCase,
    val deleteUserUseCase: DeleteUserUseCase,
    val reAuthenticateUseCase: ReAuthenticateUseCase
) : ViewModel() {

    private val _reAuthenticateState = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val reAuthenticateState = _reAuthenticateState.asStateFlow()
    private val _loginState = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val loginState = _loginState.asStateFlow()
    private val _userDetailsFlow = MutableStateFlow<UiState<UserDetails>>(UiState.Idle)
    var userDetailsFlow = _userDetailsFlow.asStateFlow()
    private val _logoutFlow = MutableStateFlow<UiState<Boolean>>(UiState.Idle)
    var logoutFlow = _logoutFlow.asStateFlow()
    private val _updateDetailsFlow = MutableStateFlow<UiState<String>>(UiState.Idle)
    var updateDetailsFlow = _updateDetailsFlow.asStateFlow()
    private var userDetails: UserDetails? = null

    init {
        loadUserDetails()
    }

    /**
     * Attempt login with provided credentials
     */
    fun changePassword(
        password: String,
        retypePassword: String
    ) {
        viewModelScope.launch {
            _loginState.value = UiState.Loading
            if (password.isNotEmpty() && password == retypePassword) {
                changePasswordUseCase(password).collectLatest { login ->
                    when (login) {
                        is DataState.Success -> {
                            _loginState.value = UiState.Success(login.data)
                        }

                        is DataState.Error -> {
                            _loginState.value = UiState.Error(
                                1,
                                login.message ?: "User is not logged in"
                            )
                        }
                    }
                }
            } else {
                _loginState.value = UiState.Error(
                    1, "Passwords are not same"
                )
            }
        }
    }

    /**
     * Reset error message
     */
    fun resetErrorMessage(field: String) {
        when(field) {
            AppConstants.EditField.PASSWORD -> {
                _loginState.value = UiState.Idle
            }
        }
    }

    /**
     * Load present user details
     */
    private fun loadUserDetails() {
        viewModelScope.launch {
            _userDetailsFlow.value = UiState.Loading
            userDetailsUseCase().collectLatest { user ->
                when(user) {
                    is DataState.Success -> {
                        LoggerUtils.traceLog("loadUserDetails>>> location${user.data.location}")
                        userDetails = user.data
                        _userDetailsFlow.value = UiState.Success(user.data)
                    }
                    is DataState.Error -> {
                        _userDetailsFlow.value = UiState.Error(
                            1,
                            user.message ?: ""
                        )
                    }
                }
            }
        }
    }

    /**
     * Update user details
     */
    fun updateUserDetails(
        email: String? = null,
        name: String? = null,
        location: String? = null
    ) {
        viewModelScope.launch {
            userDetails?.let { userItem ->
                userItem.name = name ?: userItem.name
                userItem.email = email ?: userItem.email
                userItem.location = location ?: userItem.location
                updateUserDetailsUseCase(userItem).collectLatest {
                    when(it) {
                        is DataState.Success -> {
                            LoggerUtils.traceLog("updateUserDetails -> success")
                            _updateDetailsFlow.value = UiState.Success(
                                if (name != null) {
                                    "Name changed successfully"
                                } else if (location != null) {
                                    "Location changed successfully"
                                } else {
                                    "User details changed successfully"
                                }
                            )
                        }
                        is DataState.Error -> {
                            LoggerUtils.traceLog("updateUserDetails -> error")
                            _updateDetailsFlow.value = UiState.Error(
                                1,
                                it.message ?: ""
                            )
                        }
                    }
                }
            }
        }
    }

    /**
     * Logout the user
     */
    fun logoutUser() {
        viewModelScope.launch {
            _logoutFlow.value = UiState.Loading
            logoutUserUseCase().collectLatest { state ->
                when(state) {
                    is DataState.Success -> {
                        _logoutFlow.value = UiState.Success(state.data)
                    }
                    is DataState.Error -> {
                        _logoutFlow.value = UiState.Error(
                            state.errorCode ?: 1,
                            state.message ?: ""
                        )
                    }
                }
            }
        }
    }

    /**
     * Delete the user
     */
    fun deleteUser() {
        viewModelScope.launch {
            _logoutFlow.value = UiState.Loading
            userDetails?.let { user ->
                deleteUserUseCase(user).collectLatest { state ->
                    when(state) {
                        is DataState.Success -> {
                            _logoutFlow.value = UiState.Success(state.data)
                        }
                        is DataState.Error -> {
                            _logoutFlow.value = UiState.Error(
                                state.errorCode ?: 1,
                                state.message ?: ""
                            )
                        }
                    }
                }
            }
        }
    }

    /**
     * Re Authenticate the user
     */
    fun reAuthenticateUser(email: String, password: String) {
        viewModelScope.launch {
            _reAuthenticateState.value = UiState.Loading
            if (email.isEmpty()) {
                _reAuthenticateState.value = UiState.Error(
                    1, "Email is empty"
                )
            } else if (!email.isValidEmail()) {
                _reAuthenticateState.value = UiState.Error(
                    1, "Email is not valid"
                )
            } else if (password.isEmpty()) {
                _reAuthenticateState.value = UiState.Error(
                    1, "Password is empty"
                )
            } else {
                reAuthenticateUseCase(
                    email, password
                ).collectLatest { state ->
                    when(state) {
                        is DataState.Success -> {
                            _reAuthenticateState.value = UiState.Success(state.data)
                        }
                        is DataState.Error -> {
                            _reAuthenticateState.value = UiState.Error(
                                state.errorCode ?: 1,
                                state.message ?: ""
                            )
                        }
                    }
                }
            }
        }
    }
}
