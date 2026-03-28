package com.example.amazonsample.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.amazonsample.core.database.dao.*
import com.example.amazonsample.core.database.entity.*

@Database(
    entities = [
        ProductEntity::class,
        UserEntity::class,
        OrderEntity::class,
        OrderProductEntity::class,
        CartItemEntity::class,
        WishlistItemEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AmazonDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDao
    abstract fun orderDao(): OrderDao
    abstract fun orderProductDao(): OrderProductDao
    abstract fun cartDao(): CartDao
    abstract fun wishlistDao(): WishlistDao
} 