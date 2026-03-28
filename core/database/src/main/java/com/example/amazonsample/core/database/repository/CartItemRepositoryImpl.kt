package com.example.amazonsample.core.database.repository

import com.example.amazonsample.core.common.Result
import com.example.amazonsample.core.common.domain.model.CartItem
import com.example.amazonsample.core.common.domain.model.Product
import com.example.amazonsample.core.common.domain.repository.CartRepository
import com.example.amazonsample.core.database.dao.CartDao
import com.example.amazonsample.core.database.dao.ProductDao
import com.example.amazonsample.core.database.entity.CartItemEntity
import com.example.amazonsample.core.database.entity.ProductEntity
import com.example.amazonsample.core.network.ApiService
import com.example.amazonsample.core.network.model.CartItemDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartItemRepositoryImpl @Inject constructor(
    private val cartDao: CartDao,
    private val productDao: ProductDao,
    private val apiService: ApiService
) : CartRepository {

    override suspend fun getCartItems(): Result<List<CartItem>> {
        return try {
            val cartItemEntities = cartDao.getCartItems().firstOrNull() ?: emptyList()
            val productIds = cartItemEntities.map { it.productId }
            val productEntities = productIds.mapNotNull { productId ->
                productDao.getProduct(productId).firstOrNull()
            }
            val productMap = productEntities.associateBy { it.id }
            val result = cartItemEntities.mapNotNull { cartItemEntity ->
                productMap[cartItemEntity.productId]?.let { productEntity ->
                    cartItemEntity.toCartItem(productEntity)
                }
            }
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateCartItem(cartItem: CartItem): Result<CartItem> {
        return try {
            val cartItemEntity = cartItem.toCartItemEntity()
            cartDao.insertCartItem(cartItemEntity)
            Result.success(cartItem)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun removeFromCart(productId: String): Result<Unit> {
        return try {
            val cartItemEntity = cartDao.getCartItem(productId).firstOrNull()
            if (cartItemEntity != null) {
                cartDao.deleteCartItem(cartItemEntity)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun clearCart(): Result<Unit> {
        return try {
            cartDao.clearCart()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun delete(id: String): Result<Unit> {
        return try {
            val cartItemEntity = cartDao.getCartItem(id).firstOrNull()
            if (cartItemEntity != null) {
                cartDao.deleteCartItem(cartItemEntity)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun get(id: String): Result<CartItem> {
        return try {
            val cartItemEntity = cartDao.getCartItem(id).firstOrNull()
            if (cartItemEntity == null) {
                Result.failure(Exception("Cart item not found"))
            } else {
                val productEntity = productDao.getProduct(cartItemEntity.productId).firstOrNull()
                if (productEntity == null) {
                    Result.failure(Exception("Product not found"))
                } else {
                    Result.success(cartItemEntity.toCartItem(productEntity))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAll(): Result<List<CartItem>> = getCartItems()

    override suspend fun insert(item: CartItem): Result<Unit> {
        return try {
            val cartItemEntity = item.toCartItemEntity()
            cartDao.insertCartItem(cartItemEntity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun observe(id: String): Flow<Result<CartItem>> {
        return cartDao.getCartItem(id).map { cartItemEntity ->
            if (cartItemEntity == null) {
                Result.failure(Exception("Cart item not found"))
            } else {
                val productEntity = productDao.getProduct(cartItemEntity.productId).firstOrNull()
                if (productEntity == null) {
                    Result.failure(Exception("Product not found"))
                } else {
                    Result.success(cartItemEntity.toCartItem(productEntity))
                }
            }
        }
    }

    override fun observeAll(): Flow<Result<List<CartItem>>> {
        return cartDao.getCartItems().map { cartItemEntities ->
            try {
                val productIds = cartItemEntities.map { it.productId }
                val productEntities = productIds.mapNotNull { productId ->
                    productDao.getProduct(productId).firstOrNull()
                }
                val productMap = productEntities.associateBy { it.id }
                val result = cartItemEntities.mapNotNull { cartItemEntity ->
                    productMap[cartItemEntity.productId]?.let { productEntity ->
                        cartItemEntity.toCartItem(productEntity)
                    }
                }
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun update(item: CartItem): Result<Unit> {
        return try {
            val cartItemEntity = item.toCartItemEntity()
            cartDao.insertCartItem(cartItemEntity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun CartItemEntity.toCartItem(productEntity: ProductEntity): CartItem {
        return CartItem(
            id = productId,
            product = productEntity.toProduct(),
            quantity = quantity,
            price = productEntity.price
        )
    }

    private fun CartItem.toCartItemEntity(): CartItemEntity {
        return CartItemEntity(
            productId = product.id,
            quantity = quantity
        )
    }

    private fun ProductEntity.toProduct(): Product {
        return Product(
            id = id,
            name = name,
            description = description,
            price = price,
            imageUrl = imageUrl,
            category = category,
            rating = rating,
            reviewCount = reviewCount,
            inStock = inStock,
            discount = discount
        )
    }
} 