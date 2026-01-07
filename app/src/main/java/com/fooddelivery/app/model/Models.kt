package com.fooddelivery.app.model

data class Restaurant(
    val id: String,
    val name: String,
    val description: String,
    val rating: Float,
    val deliveryTime: String,
    val cuisineType: String,
    val imageUrl: String = "",
    val menu: List<FoodItem> = emptyList()
)

data class FoodItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val category: String,
    val imageUrl: String = "",
    val isVegetarian: Boolean = false,
    val restaurantId: String
)

data class CartItem(
    val foodItem: FoodItem,
    var quantity: Int = 1
) {
    val totalPrice: Double
        get() = foodItem.price * quantity
}

data class Order(
    val id: String,
    val items: List<CartItem>,
    val totalAmount: Double,
    val status: OrderStatus,
    val timestamp: Long,
    val deliveryAddress: String
)

enum class OrderStatus {
    PENDING,
    CONFIRMED,
    PREPARING,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED
}
