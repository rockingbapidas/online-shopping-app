package com.example.amazonsample.feature.orders.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amazonsample.core.common.domain.model.Order
import com.example.amazonsample.feature.orders.domain.usecase.GetOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val getOrdersUseCase: GetOrdersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(OrdersState())
    val uiState: StateFlow<OrdersState> = _uiState.asStateFlow()

    init {
        loadOrders()
    }

    private fun loadOrders() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = getOrdersUseCase()) {
                is com.example.amazonsample.core.common.Result.Success -> {
                    _uiState.update {
                        it.copy(
                            orders = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                }
                is com.example.amazonsample.core.common.Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.exception.message ?: "Failed to load orders"
                        )
                    }
                }
                is com.example.amazonsample.core.common.Result.Loading -> {
                    // Optionally handle loading state
                }
            }
        }
    }
} 