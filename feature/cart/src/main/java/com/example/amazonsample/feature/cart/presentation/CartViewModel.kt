package com.example.amazonsample.feature.cart.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amazonsample.core.common.domain.model.CartItem
import com.example.amazonsample.feature.cart.domain.usecase.GetCartItemsUseCase
import com.example.amazonsample.feature.cart.domain.usecase.RemoveFromCartUseCase
import com.example.amazonsample.feature.cart.domain.usecase.CheckoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val checkoutUseCase: CheckoutUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartState())
    val uiState: StateFlow<CartState> = _uiState.asStateFlow()

    init {
        loadCartItems()
    }

    private fun loadCartItems() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                when (val result = getCartItemsUseCase()) {
                    is com.example.amazonsample.core.common.Result.Success -> {
                        val items = result.data
                        val total = items.sumOf { it.price }
                        _uiState.update {
                            it.copy(
                                cartItems = items,
                                total = total,
                                isLoading = false,
                                error = null
                            )
                        }
                    }
                    is com.example.amazonsample.core.common.Result.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = result.exception.message ?: "Failed to load cart items"
                            )
                        }
                    }
                    else -> {}
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load cart items"
                    )
                }
            }
        }
    }

    fun removeFromCart(itemId: String) {
        viewModelScope.launch {
            try {
                when (val result = removeFromCartUseCase(itemId)) {
                    is com.example.amazonsample.core.common.Result.Success -> {
                        loadCartItems()
                    }
                    is com.example.amazonsample.core.common.Result.Error -> {
                        _uiState.update {
                            it.copy(
                                error = result.exception.message ?: "Failed to remove item from cart"
                            )
                        }
                    }
                    else -> {}
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.message ?: "Failed to remove item from cart"
                    )
                }
            }
        }
    }

    fun checkout() {
        viewModelScope.launch {
            try {
                when (val result = checkoutUseCase()) {
                    is com.example.amazonsample.core.common.Result.Success -> {
                        _uiState.update { it.copy(cartItems = emptyList(), total = 0.0) }
                    }
                    is com.example.amazonsample.core.common.Result.Error -> {
                        _uiState.update {
                            it.copy(
                                error = result.exception.message ?: "Failed to checkout"
                            )
                        }
                    }
                    else -> {}
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.message ?: "Failed to checkout"
                    )
                }
            }
        }
    }
} 