package com.example.amazonsample.core.common.domain.model

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val category: String,
    val rating: Double,
    val reviewCount: Int,
    val inStock: Boolean,
    val discount: Double? = null
) 