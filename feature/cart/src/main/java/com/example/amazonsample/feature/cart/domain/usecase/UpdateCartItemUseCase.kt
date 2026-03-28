package com.example.amazonsample.feature.cart.domain.usecase

import com.example.amazonsample.core.common.Result
import com.example.amazonsample.core.common.domain.model.CartItem
import com.example.amazonsample.core.common.domain.repository.CartRepository
import javax.inject.Inject

class UpdateCartItemUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(cartItem: CartItem): Result<CartItem> {
        return repository.updateCartItem(cartItem)
    }
} 