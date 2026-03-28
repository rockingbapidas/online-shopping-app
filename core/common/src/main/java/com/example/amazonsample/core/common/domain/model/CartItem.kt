package com.example.amazonsample.core.common.domain.model

data class CartItem(
    val id: String,
    val product: Product,
    val quantity: Int,
    val price: Double
) 