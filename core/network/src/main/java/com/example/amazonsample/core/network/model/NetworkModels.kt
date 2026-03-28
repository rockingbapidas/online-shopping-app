package com.example.amazonsample.core.network.model

data class ProductDto(
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

data class UserDto(
    val id: String,
    val email: String,
    val name: String,
    val token: String
)

data class OrderDto(
    val id: String,
    val userId: String,
    val products: List<OrderProductDto>,
    val totalAmount: Double,
    val status: String,
    val createdAt: String,
    val shippingAddress: AddressDto
)

data class OrderProductDto(
    val productId: String,
    val quantity: Int,
    val price: Double
)

data class AddressDto(
    val street: String,
    val city: String,
    val state: String,
    val country: String,
    val zipCode: String
)

data class CartItemDto(
    val productId: String,
    val quantity: Int,
    val price: Double
)

data class WishlistItemDto(
    val productId: String,
    val addedDate: Long
) 