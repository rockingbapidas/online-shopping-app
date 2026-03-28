package com.example.amazonsample.core.database.repository

import com.example.amazonsample.core.common.Result
import com.example.amazonsample.core.common.domain.model.CartItem
import com.example.amazonsample.core.common.domain.model.Order
import com.example.amazonsample.core.common.domain.model.OrderStatus
import com.example.amazonsample.core.common.domain.model.Product
import com.example.amazonsample.core.common.domain.repository.BaseRepository
import com.example.amazonsample.core.database.dao.OrderDao
import com.example.amazonsample.core.database.dao.ProductDao
import com.example.amazonsample.core.database.entity.OrderEntity
import com.example.amazonsample.core.database.entity.OrderProductEntity
import com.example.amazonsample.core.database.entity.ProductEntity
import com.example.amazonsample.core.network.ApiService
import com.example.amazonsample.core.network.model.OrderDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDao: OrderDao,
    private val productDao: ProductDao,
    private val apiService: ApiService
) : BaseRepository<Order> {

    override suspend fun get(id: String): Result<Order> {
        return try {
            val orderEntity = orderDao.getOrder(id).firstOrNull()
            if (orderEntity == null) {
                Result.failure(Exception("Order not found"))
            } else {
                // Fetch order products and products here if needed
                Result.success(orderEntity.toOrder(emptyList()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAll(): Result<List<Order>> {
        return try {
            val orderEntities = orderDao.getOrders("").firstOrNull() ?: emptyList()
            // Fetch order products and products here if needed
            Result.success(orderEntities.map { it.toOrder(emptyList()) })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun insert(item: Order): Result<Unit> {
        return try {
            val orderEntity = item.toOrderEntity()
            orderDao.insertOrder(orderEntity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun update(item: Order): Result<Unit> {
        return try {
            val orderEntity = item.toOrderEntity()
            orderDao.insertOrder(orderEntity) // Using insert with REPLACE strategy
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun delete(id: String): Result<Unit> {
        return try {
            val orderEntity = orderDao.getOrder(id).firstOrNull()
            if (orderEntity != null) {
                orderDao.deleteOrder(orderEntity)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun observe(id: String): Flow<Result<Order>> {
        return orderDao.getOrder(id).map { orderEntity ->
            if (orderEntity == null) {
                Result.failure(Exception("Order not found"))
            } else {
                // Fetch order products and products here if needed
                Result.success(orderEntity.toOrder(emptyList()))
            }
        }
    }

    override fun observeAll(): Flow<Result<List<Order>>> {
        return orderDao.getOrders("").map { orderEntities ->
            try {
                // Fetch order products and products here if needed
                Result.success(orderEntities.map { it.toOrder(emptyList()) })
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    private fun OrderEntity.toOrder(items: List<CartItem>): Order {
        return Order(
            id = id,
            items = items,
            totalAmount = totalAmount,
            status = OrderStatus.valueOf(status),
            orderDate = java.util.Date(),
            deliveryAddress = shippingAddress
        )
    }

    private fun Order.toOrderEntity(): OrderEntity {
        return OrderEntity(
            id = id,
            userId = "", // You may want to add this
            totalAmount = totalAmount,
            status = status.name,
            createdAt = orderDate.toString(),
            shippingAddress = deliveryAddress
        )
    }
} 