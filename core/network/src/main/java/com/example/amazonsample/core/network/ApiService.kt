package com.example.amazonsample.core.network

import com.example.amazonsample.core.network.model.*
import retrofit2.http.*

interface ApiService {
    @GET("products")
    suspend fun getProducts(): List<ProductDto>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: String): ProductDto

    @POST("auth/login")
    suspend fun login(
        @Body credentials: Map<String, String>
    ): UserDto

    @POST("auth/register")
    suspend fun register(
        @Body userData: Map<String, String>
    ): UserDto

    @GET("orders")
    suspend fun getOrders(): List<OrderDto>

    @GET("orders/{id}")
    suspend fun getOrder(@Path("id") id: String): OrderDto

    @POST("orders")
    suspend fun createOrder(
        @Body orderData: Map<String, Any>
    ): OrderDto

    @GET("cart")
    suspend fun getCartItems(): List<CartItemDto>

    @GET("cart/{id}")
    suspend fun getCartItem(@Path("id") id: String): CartItemDto

    @POST("cart/add")
    suspend fun addToCart(
        @Body cartItem: CartItemDto
    ): CartItemDto

    @PUT("cart/{id}")
    suspend fun updateCartItem(
        @Path("id") id: String,
        @Body cartItem: CartItemDto
    ): CartItemDto

    @DELETE("cart/{id}")
    suspend fun removeFromCart(
        @Path("id") id: String
    ): Unit

    @GET("wishlist")
    suspend fun getWishlistItems(): List<WishlistItemDto>

    @GET("wishlist/{id}")
    suspend fun getWishlistItem(@Path("id") id: String): WishlistItemDto

    @POST("wishlist/add")
    suspend fun addToWishlist(
        @Body wishlistItem: WishlistItemDto
    ): WishlistItemDto

    @DELETE("wishlist/{id}")
    suspend fun removeFromWishlist(
        @Path("id") id: String
    ): Unit
} 