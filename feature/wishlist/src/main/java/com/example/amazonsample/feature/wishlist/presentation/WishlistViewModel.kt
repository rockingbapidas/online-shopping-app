package com.example.amazonsample.feature.wishlist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amazonsample.core.common.domain.model.WishlistItem
import com.example.amazonsample.core.common.Result
import com.example.amazonsample.feature.wishlist.domain.usecase.GetWishlistItemsUseCase
import com.example.amazonsample.feature.wishlist.domain.usecase.RemoveFromWishlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val getWishlistItemsUseCase: GetWishlistItemsUseCase,
    private val removeFromWishlistUseCase: RemoveFromWishlistUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(WishlistState())
    val uiState: StateFlow<WishlistState> = _uiState.asStateFlow()

    init {
        loadWishlistItems()
    }

    private fun loadWishlistItems() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = getWishlistItemsUseCase()) {
                is Result.Success -> {
                    _uiState.update { 
                        it.copy(
                            wishlistItems = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            error = result.exception.message ?: "Failed to load wishlist items"
                        )
                    }
                }
                is Result.Loading -> {
                    // Optionally handle loading state
                }
            }
        }
    }

    fun removeFromWishlist(itemId: String) {
        viewModelScope.launch {
            val wishlistItem = uiState.value.wishlistItems.find { it.id == itemId }
            if (wishlistItem != null) {
                when (val result = removeFromWishlistUseCase(wishlistItem)) {
                    is Result.Success -> {
                        loadWishlistItems() // Reload wishlist items after removal
                    }
                    is Result.Error -> {
                        _uiState.update { 
                            it.copy(
                                error = result.exception.message ?: "Failed to remove item from wishlist"
                            )
                        }
                    }
                    is Result.Loading -> {
                        // Optionally handle loading state
                    }
                }
            }
        }
    }
} 