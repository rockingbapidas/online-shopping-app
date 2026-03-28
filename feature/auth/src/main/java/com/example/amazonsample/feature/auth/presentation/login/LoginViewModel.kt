package com.example.amazonsample.feature.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amazonsample.core.common.Result
import com.example.amazonsample.feature.auth.domain.model.User
import com.example.amazonsample.feature.auth.domain.usecase.LoginUseCase
import com.example.amazonsample.feature.auth.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChange -> {
                _state.value = _state.value.copy(email = event.email)
            }
            is LoginEvent.OnPasswordChange -> {
                _state.value = _state.value.copy(password = event.password)
            }
            is LoginEvent.OnNameChange -> {
                _state.value = _state.value.copy(name = event.name)
            }
            is LoginEvent.OnLoginClick -> {
                login()
            }
            is LoginEvent.OnRegisterClick -> {
                register()
            }
            is LoginEvent.OnToggleAuthMode -> {
                _state.value = _state.value.copy(
                    isRegistering = !_state.value.isRegistering
                )
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val result = loginUseCase(_state.value.email, _state.value.password)
            _state.value = _state.value.copy(
                isLoading = false,
                error = (result as? Result.Error)?.exception?.message
            )
            if (result is Result.Success) {
                _state.value = _state.value.copy(
                    user = result.data,
                    error = null
                )
            }
        }
    }

    private fun register() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val result = registerUseCase(
                _state.value.name,
                _state.value.email,
                _state.value.password
            )
            _state.value = _state.value.copy(
                isLoading = false,
                error = (result as? Result.Error)?.exception?.message
            )
            if (result is Result.Success) {
                _state.value = _state.value.copy(
                    user = result.data,
                    error = null
                )
            }
        }
    }
} 