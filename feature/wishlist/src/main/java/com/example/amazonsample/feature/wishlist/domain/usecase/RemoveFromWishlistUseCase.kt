package com.example.amazonsample.feature.wishlist.domain.usecase

import com.example.amazonsample.core.common.Result
import com.example.amazonsample.core.common.domain.model.WishlistItem
import com.example.amazonsample.core.common.domain.repository.BaseRepository
import javax.inject.Inject

class RemoveFromWishlistUseCase @Inject constructor(
    private val repository: BaseRepository<WishlistItem>
) {
    suspend operator fun invoke(item: WishlistItem): Result<Unit> {
        return repository.delete(item.id)
    }
} 