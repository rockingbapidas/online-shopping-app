package com.example.amazonsample.feature.cart.domain.usecase

import com.example.amazonsample.core.common.Result
import com.example.amazonsample.core.common.domain.repository.CartRepository
import javax.inject.Inject

class RemoveFromCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(productId: String): Result<Unit> {
        return repository.removeFromCart(productId)
    }
} 