package com.example.amazonsample.core.database.di

import android.content.Context
import androidx.room.Room
import com.example.amazonsample.core.database.AmazonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AmazonDatabase {
        return Room.databaseBuilder(
            context,
            AmazonDatabase::class.java,
            "amazon_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideProductDao(database: AmazonDatabase) = database.productDao()

    @Provides
    @Singleton
    fun provideUserDao(database: AmazonDatabase) = database.userDao()

    @Provides
    @Singleton
    fun provideOrderDao(database: AmazonDatabase) = database.orderDao()

    @Provides
    @Singleton
    fun provideOrderProductDao(database: AmazonDatabase) = database.orderProductDao()

    @Provides
    @Singleton
    fun provideCartDao(database: AmazonDatabase) = database.cartDao()

    @Provides
    @Singleton
    fun provideWishlistDao(database: AmazonDatabase) = database.wishlistDao()
} 