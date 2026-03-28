package com.example.amazonsample.feature.cart.presentation

import com.example.amazonsample.core.common.domain.model.CartItem

data class CartState(
    val cartItems: List<CartItem> = emptyList(),
    val total: Double = 0.0,
    val isLoading: Boolean = false,
    val error: String? = null
) 