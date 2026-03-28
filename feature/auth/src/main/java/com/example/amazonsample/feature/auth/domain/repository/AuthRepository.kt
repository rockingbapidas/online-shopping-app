package com.example.amazonsample.feature.auth.domain.repository

import com.example.amazonsample.core.common.Result
import com.example.amazonsample.feature.auth.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(name: String, email: String, password: String): Result<User>
    suspend fun logout()
    fun getCurrentUser(): Flow<Result<User?>>
    suspend fun saveUser(user: User)
    suspend fun clearUser()
} 