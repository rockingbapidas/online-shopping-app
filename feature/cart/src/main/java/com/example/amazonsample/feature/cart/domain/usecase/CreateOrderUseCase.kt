package com.example.amazonsample.feature.cart.domain.usecase

import com.example.amazonsample.core.common.Result
import com.example.amazonsample.core.common.domain.model.Order
import com.example.amazonsample.core.common.domain.repository.OrderRepository
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(order: Order): Result<Order> {
        return repository.createOrder(order)
    }
} 