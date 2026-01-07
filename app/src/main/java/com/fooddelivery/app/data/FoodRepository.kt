package com.fooddelivery.app.data

import com.fooddelivery.app.model.CartItem
import com.fooddelivery.app.model.FoodItem
import com.fooddelivery.app.model.Restaurant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FoodRepository {
    
    private val _cart = MutableStateFlow<List<CartItem>>(emptyList())
    val cart: StateFlow<List<CartItem>> = _cart.asStateFlow()
    
    fun getRestaurants(): List<Restaurant> {
        return SampleData.restaurants
    }
    
    fun getRestaurantById(id: String): Restaurant? {
        return SampleData.restaurants.find { it.id == id }
    }
    
    fun addToCart(foodItem: FoodItem) {
        val currentCart = _cart.value.toMutableList()
        val existingItem = currentCart.find { it.foodItem.id == foodItem.id }
        
        if (existingItem != null) {
            existingItem.quantity++
        } else {
            currentCart.add(CartItem(foodItem, 1))
        }
        
        _cart.value = currentCart
    }
    
    fun removeFromCart(foodItem: FoodItem) {
        val currentCart = _cart.value.toMutableList()
        val existingItem = currentCart.find { it.foodItem.id == foodItem.id }
        
        if (existingItem != null) {
            if (existingItem.quantity > 1) {
                existingItem.quantity--
            } else {
                currentCart.remove(existingItem)
            }
        }
        
        _cart.value = currentCart
    }
    
    fun clearCart() {
        _cart.value = emptyList()
    }
    
    fun getCartTotal(): Double {
        return _cart.value.sumOf { it.totalPrice }
    }
    
    companion object {
        @Volatile
        private var instance: FoodRepository? = null
        
        fun getInstance(): FoodRepository {
            return instance ?: synchronized(this) {
                instance ?: FoodRepository().also { instance = it }
            }
        }
    }
}
