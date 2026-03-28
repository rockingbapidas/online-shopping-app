package com.example.amazonsample.feature.home.domain.usecase

import com.example.amazonsample.core.common.Result
import com.example.amazonsample.core.common.domain.model.Product
import com.example.amazonsample.core.common.domain.repository.BaseRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: BaseRepository<Product>
) {
    suspend operator fun invoke(): Result<List<Product>> {
        return repository.getAll()
    }
} 