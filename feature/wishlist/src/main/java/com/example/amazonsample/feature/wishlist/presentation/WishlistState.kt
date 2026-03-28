package com.example.amazonsample.feature.wishlist.presentation

import com.example.amazonsample.core.common.domain.model.WishlistItem

data class WishlistState(
    val wishlistItems: List<WishlistItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) 