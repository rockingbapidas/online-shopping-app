package com.example.amazonsample.feature.product.presentation

import com.example.amazonsample.core.common.domain.model.Product

data class ProductDetailState(
    val product: Product? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) 