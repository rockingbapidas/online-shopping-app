package com.example.amazonsample.core.database.di

import com.example.amazonsample.core.common.domain.repository.BaseRepository
import com.example.amazonsample.core.common.domain.model.Order
import com.example.amazonsample.core.database.dao.OrderDao
import com.example.amazonsample.core.database.dao.ProductDao
import com.example.amazonsample.core.database.repository.OrderRepositoryImpl
import com.example.amazonsample.core.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OrderRepositoryModule {

    @Provides
    @Singleton
    fun provideOrderRepository(
        orderDao: OrderDao,
        productDao: ProductDao,
        apiService: ApiService
    ): BaseRepository<Order> {
        return OrderRepositoryImpl(orderDao, productDao, apiService)
    }
} 