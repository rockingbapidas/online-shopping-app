package com.example.amazonsample.feature.orders.presentation

import com.example.amazonsample.core.common.domain.model.Order

data class OrdersState(
    val orders: List<Order> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) 