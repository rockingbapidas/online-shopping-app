package com.example.amazonsample.feature.product.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amazonsample.feature.product.domain.usecase.GetProductUseCase
import com.example.amazonsample.feature.product.domain.usecase.AddToCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val addToCartUseCase: AddToCartUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailState())
    val uiState: StateFlow<ProductDetailState> = _uiState.asStateFlow()

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val product = getProductUseCase(productId)
                _uiState.update { 
                    it.copy(
                        product = product.getOrNull(),
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load product"
                    )
                }
            }
        }
    }

    fun addToCart() {
        viewModelScope.launch {
            try {
                uiState.value.product?.let { product ->
                    // Create a CartItem from the product details
                    val cartItem = com.example.amazonsample.core.common.domain.model.CartItem(
                        id = product.id,
                        product = product,
                        quantity = 1,
                        price = product.price
                    )
                    addToCartUseCase(cartItem)
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        error = e.message ?: "Failed to add to cart"
                    )
                }
            }
        }
    }
} 