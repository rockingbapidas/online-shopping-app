package com.example.amazonsample.core.database.dao

import androidx.room.*
import com.example.amazonsample.core.database.entity.WishlistItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WishlistItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWishlistItem(wishlistItem: WishlistItemEntity)

    @Update
    suspend fun updateWishlistItem(wishlistItem: WishlistItemEntity)

    @Delete
    suspend fun deleteWishlistItem(wishlistItem: WishlistItemEntity)

    @Query("SELECT * FROM wishlist_items")
    fun getAllWishlistItems(): Flow<List<WishlistItemEntity>>

    @Query("SELECT * FROM wishlist_items WHERE productId = :productId")
    suspend fun getWishlistItemById(productId: String): WishlistItemEntity?
} 