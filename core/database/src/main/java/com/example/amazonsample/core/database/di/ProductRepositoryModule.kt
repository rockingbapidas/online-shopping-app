package com.example.amazonsample.core.database.di

import com.example.amazonsample.core.common.domain.model.Product
import com.example.amazonsample.core.common.domain.repository.BaseRepository
import com.example.amazonsample.core.database.dao.ProductDao
import com.example.amazonsample.core.database.repository.ProductRepositoryImpl
import com.example.amazonsample.core.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductRepositoryModule {

    @Provides
    @Singleton
    fun provideProductRepository(
        productDao: ProductDao,
        apiService: ApiService
    ): BaseRepository<Product> {
        return ProductRepositoryImpl(productDao, apiService)
    }
} 