package com.example.amazonsample.feature.auth.domain.usecase

import com.example.amazonsample.core.common.Result
import com.example.amazonsample.feature.auth.domain.model.User
import com.example.amazonsample.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(name: String, email: String, password: String): Result<User> {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            return Result.Error(Exception("All fields are required"))
        }
        if (password.length < 6) {
            return Result.Error(Exception("Password must be at least 6 characters"))
        }
        return repository.register(name, email, password)
    }
} 