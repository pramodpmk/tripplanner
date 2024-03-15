package com.example.composeapp.ui.presentation.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeapp.data.UiState
import com.example.composeapp.data.login.LoginModel
import com.example.composeapp.data.remote.DataState
import com.example.composeapp.ui.presentation.welcome.domain.AutoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    val autoLoginUseCase: AutoLoginUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow<UiState<LoginModel>>(UiState.Loading)
    val loginState = _loginState.asStateFlow()

    init {
        viewModelScope.launch {
            attemptAutoLogin()
        }
    }

    /**
     * Attempt login with provided credentials
     */
    private suspend fun attemptAutoLogin() {
        autoLoginUseCase().collectLatest { login ->
            when (login) {
                is DataState.Success -> {
                    val userId = login.data.uid
                    _loginState.value = UiState.Success(LoginModel(userId))
                }

                is DataState.Error -> {
                    _loginState.value = UiState.Error(
                        1, "User is not logged in"
                    )
                }
            }
        }
    }
}
