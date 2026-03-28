package com.example.amazonsample.core.database.di

import com.example.amazonsample.core.common.domain.repository.BaseRepository
import com.example.amazonsample.core.common.domain.model.WishlistItem
import com.example.amazonsample.core.database.dao.WishlistDao
import com.example.amazonsample.core.database.dao.ProductDao
import com.example.amazonsample.core.database.repository.WishlistItemRepositoryImpl
import com.example.amazonsample.core.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WishlistItemRepositoryModule {

    @Provides
    @Singleton
    fun provideWishlistItemRepository(
        wishlistDao: WishlistDao,
        productDao: ProductDao,
        apiService: ApiService
    ): BaseRepository<WishlistItem> {
        return WishlistItemRepositoryImpl(wishlistDao, productDao, apiService)
    }
} 