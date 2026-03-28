package com.example.amazonsample.core.common.domain.repository

import com.example.amazonsample.core.common.Result
import com.example.amazonsample.core.common.domain.model.Order

interface OrderRepository : BaseRepository<Order> {
    suspend fun createOrder(order: Order): Result<Order>
    suspend fun getOrderById(id: String): Result<Order>
    suspend fun getUserOrders(userId: String): Result<List<Order>>
} 