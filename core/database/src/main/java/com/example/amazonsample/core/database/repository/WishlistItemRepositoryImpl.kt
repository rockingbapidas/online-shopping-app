package com.example.amazonsample.core.database.repository

import com.example.amazonsample.core.common.Result
import com.example.amazonsample.core.common.domain.model.Product
import com.example.amazonsample.core.common.domain.model.WishlistItem
import com.example.amazonsample.core.common.domain.repository.BaseRepository
import com.example.amazonsample.core.database.dao.ProductDao
import com.example.amazonsample.core.database.dao.WishlistDao
import com.example.amazonsample.core.database.entity.ProductEntity
import com.example.amazonsample.core.database.entity.WishlistItemEntity
import com.example.amazonsample.core.network.ApiService
import com.example.amazonsample.core.network.model.WishlistItemDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WishlistItemRepositoryImpl @Inject constructor(
    private val wishlistDao: WishlistDao,
    private val productDao: ProductDao,
    private val apiService: ApiService
) : BaseRepository<WishlistItem> {

    override suspend fun get(id: String): Result<WishlistItem> {
        return try {
            val wishlistItemEntity = wishlistDao.getWishlistItem(id).firstOrNull()
            if (wishlistItemEntity == null) {
                Result.failure(Exception("Wishlist item not found"))
            } else {
                val productEntity = productDao.getProduct(wishlistItemEntity.productId).firstOrNull()
                if (productEntity == null) {
                    Result.failure(Exception("Product not found"))
                } else {
                    Result.success(wishlistItemEntity.toWishlistItem(productEntity))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAll(): Result<List<WishlistItem>> {
        return try {
            val wishlistItemEntities = wishlistDao.getWishlistItems().firstOrNull() ?: emptyList()
            val productIds = wishlistItemEntities.map { it.productId }
            val productEntities = productIds.mapNotNull { productId ->
                productDao.getProduct(productId).firstOrNull()
            }
            val productMap = productEntities.associateBy { it.id }
            val result = wishlistItemEntities.mapNotNull { wishlistItemEntity ->
                productMap[wishlistItemEntity.productId]?.let { productEntity ->
                    wishlistItemEntity.toWishlistItem(productEntity)
                }
            }
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun insert(item: WishlistItem): Result<Unit> {
        return try {
            val wishlistItemEntity = item.toWishlistItemEntity()
            wishlistDao.insertWishlistItem(wishlistItemEntity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun update(item: WishlistItem): Result<Unit> {
        return try {
            val wishlistItemEntity = item.toWishlistItemEntity()
            wishlistDao.insertWishlistItem(wishlistItemEntity) // Using insert with REPLACE strategy
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun delete(id: String): Result<Unit> {
        return try {
            val wishlistItemEntity = wishlistDao.getWishlistItem(id).firstOrNull()
            if (wishlistItemEntity != null) {
                wishlistDao.deleteWishlistItem(wishlistItemEntity)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun observe(id: String): Flow<Result<WishlistItem>> {
        return wishlistDao.getWishlistItem(id).map { wishlistItemEntity ->
            if (wishlistItemEntity == null) {
                Result.failure(Exception("Wishlist item not found"))
            } else {
                val productEntity = productDao.getProduct(wishlistItemEntity.productId).firstOrNull()
                if (productEntity == null) {
                    Result.failure(Exception("Product not found"))
                } else {
                    Result.success(wishlistItemEntity.toWishlistItem(productEntity))
                }
            }
        }
    }

    override fun observeAll(): Flow<Result<List<WishlistItem>>> {
        return wishlistDao.getWishlistItems().map { wishlistItemEntities ->
            try {
                val productIds = wishlistItemEntities.map { it.productId }
                val productEntities = productIds.mapNotNull { productId ->
                    productDao.getProduct(productId).firstOrNull()
                }
                val productMap = productEntities.associateBy { it.id }
                val result = wishlistItemEntities.mapNotNull { wishlistItemEntity ->
                    productMap[wishlistItemEntity.productId]?.let { productEntity ->
                        wishlistItemEntity.toWishlistItem(productEntity)
                    }
                }
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    private fun WishlistItemEntity.toWishlistItem(productEntity: ProductEntity): WishlistItem {
        return WishlistItem(
            id = productId,
            product = productEntity.toProduct(),
            addedDate = addedDate
        )
    }

    private fun WishlistItem.toWishlistItemEntity(): WishlistItemEntity {
        return WishlistItemEntity(
            productId = product.id,
            addedDate = addedDate
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