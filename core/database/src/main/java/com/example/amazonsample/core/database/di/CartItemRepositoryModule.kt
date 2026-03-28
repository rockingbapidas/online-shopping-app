package com.example.amazonsample.core.database.di

import com.example.amazonsample.core.common.domain.repository.BaseRepository
import com.example.amazonsample.core.common.domain.model.CartItem
import com.example.amazonsample.core.database.dao.CartDao
import com.example.amazonsample.core.database.dao.ProductDao
import com.example.amazonsample.core.database.repository.CartItemRepositoryImpl
import com.example.amazonsample.core.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CartItemRepositoryModule {

    @Provides
    @Singleton
    fun provideCartItemRepository(
        cartDao: CartDao,
        productDao: ProductDao,
        apiService: ApiService
    ): com.example.amazonsample.core.common.domain.repository.CartRepository {
        return CartItemRepositoryImpl(cartDao, productDao, apiService)
    }
} 