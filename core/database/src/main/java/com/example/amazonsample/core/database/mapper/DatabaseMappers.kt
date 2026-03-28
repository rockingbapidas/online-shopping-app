import com.example.amazonsample.core.database.entity.WishlistItemEntity
import com.example.amazonsample.core.common.domain.model.WishlistItem
import com.example.amazonsample.core.network.model.WishlistItemDto
import com.example.amazonsample.core.common.domain.model.Product

fun WishlistItemEntity.toWishlistItem(product: Product): WishlistItem {
    return WishlistItem(
        id = productId,
        product = product,
        addedDate = addedDate
    )
}

fun WishlistItem.toWishlistItemEntity(): WishlistItemEntity {
    return WishlistItemEntity(
        productId = product.id,
        addedDate = addedDate
    )
}

fun WishlistItemDto.toWishlistItem(product: Product): WishlistItem {
    return WishlistItem(
        id = productId,
        product = product,
        addedDate = addedDate
    )
} 