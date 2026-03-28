package com.example.amazonsample.feature.auth.presentation.login

import com.example.amazonsample.feature.auth.domain.model.User

data class LoginState(
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val user: User? = null,
    val isRegistering: Boolean = false
)

sealed class LoginEvent {
    data class OnEmailChange(val email: String) : LoginEvent()
    data class OnPasswordChange(val password: String) : LoginEvent()
    data class OnNameChange(val name: String) : LoginEvent()
    object OnLoginClick : LoginEvent()
    object OnRegisterClick : LoginEvent()
    object OnToggleAuthMode : LoginEvent()
} 