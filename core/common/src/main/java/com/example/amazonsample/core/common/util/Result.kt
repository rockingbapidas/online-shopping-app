package com.example.amazonsample.core.common.util

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

fun <T> success(data: T): Result<T> = Result.Success(data)
fun <T> failure(exception: Exception): Result<T> = Result.Error(exception) 