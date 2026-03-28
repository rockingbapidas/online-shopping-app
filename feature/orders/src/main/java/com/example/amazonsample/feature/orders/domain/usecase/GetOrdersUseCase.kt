package com.example.amazonsample.feature.orders.domain.usecase

import com.example.amazonsample.core.common.Result
import com.example.amazonsample.core.common.domain.model.Order
import com.example.amazonsample.core.common.domain.repository.BaseRepository
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(
    private val repository: BaseRepository<Order>
) {
    suspend operator fun invoke(): Result<List<Order>> {
        return repository.getAll()
    }
} 