package com.example.amazonsample.core.common.domain.repository

import com.example.amazonsample.core.common.Result
import com.example.amazonsample.core.common.domain.model.CartItem

interface CartRepository : BaseRepository<CartItem> {
    suspend fun getCartItems(): Result<List<CartItem>>
    suspend fun updateCartItem(cartItem: CartItem): Result<CartItem>
    suspend fun removeFromCart(productId: String): Result<Unit>
    suspend fun clearCart(): Result<Unit>
} 