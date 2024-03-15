package com.example.composeapp.ui.presentation.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeapp.data.UiState
import com.example.composeapp.data.detail.DetailModel
import com.example.composeapp.data.home.TripItemModel
import com.example.composeapp.data.login.LoginModel
import com.example.composeapp.data.remote.DataState
import com.example.composeapp.data.search.SearchRepository
import com.example.composeapp.data.search.SearchedTripType
import com.example.composeapp.ui.presentation.login.domain.AttemptLoginUseCase
import com.example.composeapp.utils.AppConstants
import com.example.composeapp.utils.AppUtils
import com.example.composeapp.utils.AppUtils.isValidEmail
import com.example.composeapp.utils.FireStoreDataHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val attemptLoginUseCase: AttemptLoginUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow<UiState<LoginModel>>(UiState.Loading)
    val loginState = _loginState.asStateFlow()

    /**
     * Validate email and password
     */
    fun validateCredentials(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            if (email.isEmpty()) {
                _loginState.value = UiState.Error(
                    1, "Email is empty"
                )
            } else if (!email.isValidEmail()) {
                _loginState.value = UiState.Error(
                    1, "Email is not valid"
                )
            } else if (password.isEmpty()) {
                _loginState.value = UiState.Error(
                    1, "Password is empty"
                )
            } else {
                attemptLogin(email, password)
            }
        }
    }

    /**
     * Attempt login with provided credentials
     */
    private suspend fun attemptLogin(
        email: String, password: String
    ) {
       attemptLoginUseCase(
           email = email,
           password = password
       ).collectLatest { login ->
           when(login) {
               is DataState.Success -> {
                   _loginState.value = UiState.Success(login.data)
               }
               is DataState.Error -> {
                   _loginState.value = UiState.Error(
                       1, "Email or password is incorrect"
                   )
               }
           }
       }
    }
}
