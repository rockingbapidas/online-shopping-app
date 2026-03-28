package com.example.amazonsample.feature.product.domain.usecase

import com.example.amazonsample.core.common.Result
import com.example.amazonsample.core.common.domain.model.CartItem
import com.example.amazonsample.core.common.domain.repository.CartRepository
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(item: CartItem): Result<Unit> {
        return repository.insert(item)
    }
} 