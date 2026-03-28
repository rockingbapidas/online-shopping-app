package com.example.amazonsample.core.common.domain.model

import java.util.Date
import com.example.amazonsample.core.common.domain.model.CartItem

data class Order(
    val id: String,
    val items: List<CartItem>,
    val totalAmount: Double,
    val status: OrderStatus,
    val orderDate: Date,
    val deliveryAddress: String
)

enum class OrderStatus {
    PENDING,
    CONFIRMED,
    SHIPPED,
    DELIVERED,
    CANCELLED
} 