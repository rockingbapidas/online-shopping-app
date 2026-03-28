package com.example.amazonsample.feature.cart.domain.usecase

import com.example.amazonsample.core.common.Result
import com.example.amazonsample.core.common.domain.model.CartItem
import com.example.amazonsample.core.common.domain.repository.CartRepository
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(): Result<List<CartItem>> {
        return repository.getCartItems()
    }
} 