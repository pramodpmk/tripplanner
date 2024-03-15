package com.example.composeapp.ui.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeapp.data.UiState
import com.example.composeapp.data.login.LoginModel
import com.example.composeapp.data.remote.DataState
import com.example.composeapp.ui.presentation.register.domain.RegisterUserUseCase
import com.example.composeapp.utils.AppUtils.isValidEmail
import com.example.composeapp.utils.FireStoreDataHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _registerState = MutableStateFlow<UiState<LoginModel>>(UiState.Loading)
    val registerState = _registerState.asStateFlow()

    /**
     * Validate email and password
     */
    fun validateInput(
        name: String,
        email: String,
        password: String,
        location: String
    ) {
        viewModelScope.launch {
            if (name.isEmpty()) {
                _registerState.value = UiState.Error(
                    1, "Name is empty"
                )
            } else if (email.isEmpty()) {
                _registerState.value = UiState.Error(
                    1, "Email is empty"
                )
            } else if (!email.isValidEmail()) {
                _registerState.value = UiState.Error(
                    1, "Email is not valid"
                )
            } else if (password.isEmpty()) {
                _registerState.value = UiState.Error(
                    1, "Password is empty"
                )
            } else if (location.isEmpty()) {
                _registerState.value = UiState.Error(
                    1, "Location is empty"
                )
            } else {
                registerUser(name, email, password, location)
            }
        }
    }

    /**
     * Attempt login with provided credentials
     */
    private suspend fun registerUser(
        name: String, email: String, password: String, location: String
    ) {
       registerUserUseCase(
           name = name,
           email = email,
           password = password,
           location = location
       ).collectLatest { register ->
           when(register) {
               is DataState.Success -> {
                   _registerState.value = UiState.Success(register.data)
               }
               is DataState.Error -> {
                   _registerState.value = UiState.Error(
                       1, register.message ?: "Registration failed!"
                   )
               }
           }
       }
    }
}
