package com.example.amazonsample.feature.product.domain.usecase

import com.example.amazonsample.core.common.Result
import com.example.amazonsample.core.common.domain.model.Product
import com.example.amazonsample.core.common.domain.repository.BaseRepository
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val repository: BaseRepository<Product>
) {
    suspend operator fun invoke(id: String): Result<Product> {
        return repository.get(id)
    }
} 