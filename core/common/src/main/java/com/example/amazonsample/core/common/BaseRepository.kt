package com.example.amazonsample.core.common

import kotlinx.coroutines.flow.Flow

interface BaseRepository<T> {
    suspend fun get(id: String): Result<T>
    suspend fun getAll(): Result<List<T>>
    suspend fun insert(item: T): Result<Unit>
    suspend fun update(item: T): Result<Unit>
    suspend fun delete(item: T): Result<Unit>
    fun observe(id: String): Flow<Result<T>>
    fun observeAll(): Flow<Result<List<T>>>
} 