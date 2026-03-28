package com.example.amazonsample.feature.auth.domain.usecase

import com.example.amazonsample.core.common.Result
import com.example.amazonsample.feature.auth.domain.model.User
import com.example.amazonsample.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        if (email.isBlank() || password.isBlank()) {
            return Result.Error(Exception("Email and password cannot be empty"))
        }
        return repository.login(email, password)
    }
} 