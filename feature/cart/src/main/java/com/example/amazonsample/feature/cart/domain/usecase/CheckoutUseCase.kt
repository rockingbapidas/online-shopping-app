package com.example.amazonsample.feature.cart.domain.usecase

import com.example.amazonsample.core.common.Result
import com.example.amazonsample.core.common.domain.repository.CartRepository
import javax.inject.Inject

class CheckoutUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return repository.clearCart()
    }
} 