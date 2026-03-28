package com.example.amazonsample.core.database.repository

import com.example.amazonsample.core.common.Result
import com.example.amazonsample.core.common.domain.model.Product
import com.example.amazonsample.core.common.domain.repository.BaseRepository
import com.example.amazonsample.core.database.dao.ProductDao
import com.example.amazonsample.core.database.entity.ProductEntity
import com.example.amazonsample.core.network.ApiService
import com.example.amazonsample.core.network.model.ProductDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productDao: ProductDao,
    private val apiService: ApiService
) : BaseRepository<Product> {

    override suspend fun get(id: String): Result<Product> {
        return try {
            val productEntity = productDao.getProduct(id).firstOrNull()
            if (productEntity == null) {
                Result.failure(Exception("Product not found"))
            } else {
                Result.success(productEntity.toProduct())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAll(): Result<List<Product>> {
        return try {
            val productEntities = productDao.getAllProducts().firstOrNull() ?: emptyList()
            Result.success(productEntities.map { it.toProduct() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun insert(item: Product): Result<Unit> {
        return try {
            val productEntity = item.toProductEntity()
            productDao.insertProduct(productEntity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun update(item: Product): Result<Unit> {
        return try {
            val productEntity = item.toProductEntity()
            productDao.insertProduct(productEntity) // Using insert with REPLACE strategy
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun delete(id: String): Result<Unit> {
        return try {
            val productEntity = productDao.getProduct(id).firstOrNull()
            if (productEntity != null) {
                productDao.deleteProduct(productEntity)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun observe(id: String): Flow<Result<Product>> {
        return productDao.getProduct(id).map { productEntity ->
            if (productEntity == null) {
                Result.failure(Exception("Product not found"))
            } else {
                Result.success(productEntity.toProduct())
            }
        }
    }

    override fun observeAll(): Flow<Result<List<Product>>> {
        return productDao.getAllProducts().map { productEntities ->
            try {
                Result.success(productEntities.map { it.toProduct() })
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
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

    private fun Product.toProductEntity(): ProductEntity {
        return ProductEntity(
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

    private fun ProductDto.toProduct(): Product {
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