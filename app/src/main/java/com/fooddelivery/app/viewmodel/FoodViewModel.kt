package com.fooddelivery.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fooddelivery.app.data.FoodRepository
import com.fooddelivery.app.model.CartItem
import com.fooddelivery.app.model.FoodItem
import com.fooddelivery.app.model.Restaurant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FoodViewModel(
    private val repository: FoodRepository = FoodRepository.getInstance()
) : ViewModel() {
    
    private val _restaurants = MutableStateFlow<List<Restaurant>>(emptyList())
    val restaurants: StateFlow<List<Restaurant>> = _restaurants.asStateFlow()
    
    private val _selectedRestaurant = MutableStateFlow<Restaurant?>(null)
    val selectedRestaurant: StateFlow<Restaurant?> = _selectedRestaurant.asStateFlow()
    
    val cart: StateFlow<List<CartItem>> = repository.cart
    
    private val _cartTotal = MutableStateFlow(0.0)
    val cartTotal: StateFlow<Double> = _cartTotal.asStateFlow()
    
    init {
        loadRestaurants()
        viewModelScope.launch {
            repository.cart.collect { cartItems ->
                _cartTotal.value = cartItems.sumOf { it.totalPrice }
            }
        }
    }
    
    private fun loadRestaurants() {
        _restaurants.value = repository.getRestaurants()
    }
    
    fun selectRestaurant(restaurantId: String) {
        _selectedRestaurant.value = repository.getRestaurantById(restaurantId)
    }
    
    fun addToCart(foodItem: FoodItem) {
        repository.addToCart(foodItem)
    }
    
    fun removeFromCart(foodItem: FoodItem) {
        repository.removeFromCart(foodItem)
    }
    
    fun clearCart() {
        repository.clearCart()
    }
    
    fun getCartItemCount(): Int {
        return cart.value.sumOf { it.quantity }
    }
}
