package com.example.amazonsample.feature.auth.data.repository

import com.example.amazonsample.core.common.Result
import com.example.amazonsample.core.database.dao.UserDao
import com.example.amazonsample.core.database.entity.UserEntity
import com.example.amazonsample.core.network.ApiService
import com.example.amazonsample.feature.auth.domain.model.User
import com.example.amazonsample.feature.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val userDao: UserDao
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val response = api.login(mapOf("email" to email, "password" to password))
            val user = response.toUser()
            saveUser(user)
            Result.Success(user)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun register(name: String, email: String, password: String): Result<User> {
        return try {
            val response = api.register(mapOf(
                "name" to name,
                "email" to email,
                "password" to password
            ))
            val user = response.toUser()
            saveUser(user)
            Result.Success(user)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun logout() {
        clearUser()
    }

    override fun getCurrentUser(): Flow<Result<User?>> = flow {
        try {
            userDao.getUser("current")?.collect { userEntity ->
                emit(Result.Success(userEntity?.toUser()))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override suspend fun saveUser(user: User) {
        userDao.insertUser(user.toEntity())
    }

    override suspend fun clearUser() {
        userDao.getUser("current")?.collect { userEntity ->
            userEntity?.let { userDao.deleteUser(it) }
        }
    }

    private fun User.toEntity() = UserEntity(
        id = id,
        email = email,
        name = name,
        token = token
    )

    private fun UserEntity.toUser() = User(
        id = id,
        email = email,
        name = name,
        token = token
    )

    private fun com.example.amazonsample.core.network.model.UserDto.toUser() = User(
        id = id,
        email = email,
        name = name,
        token = token
    )
} 