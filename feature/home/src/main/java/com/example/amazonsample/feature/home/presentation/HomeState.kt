package com.example.amazonsample.feature.home.presentation

import com.example.amazonsample.core.common.domain.model.Product

data class HomeState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) 