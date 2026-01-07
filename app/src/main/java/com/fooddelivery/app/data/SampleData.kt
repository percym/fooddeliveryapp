package com.fooddelivery.app.data

import com.fooddelivery.app.model.Restaurant
import com.fooddelivery.app.model.FoodItem

object SampleData {
    
    private val pizzaItems = listOf(
        FoodItem(
            id = "1",
            name = "Margherita Pizza",
            description = "Classic pizza with tomato sauce, mozzarella, and basil",
            price = 12.99,
            category = "Pizza",
            isVegetarian = true,
            restaurantId = "1"
        ),
        FoodItem(
            id = "2",
            name = "Pepperoni Pizza",
            description = "Tomato sauce, mozzarella, and pepperoni slices",
            price = 14.99,
            category = "Pizza",
            restaurantId = "1"
        ),
        FoodItem(
            id = "3",
            name = "Veggie Supreme",
            description = "Loaded with fresh vegetables and cheese",
            price = 13.99,
            category = "Pizza",
            isVegetarian = true,
            restaurantId = "1"
        )
    )
    
    private val burgerItems = listOf(
        FoodItem(
            id = "4",
            name = "Classic Burger",
            description = "Beef patty with lettuce, tomato, and special sauce",
            price = 9.99,
            category = "Burgers",
            restaurantId = "2"
        ),
        FoodItem(
            id = "5",
            name = "Cheese Burger",
            description = "Beef patty with melted cheese and classic toppings",
            price = 10.99,
            category = "Burgers",
            restaurantId = "2"
        ),
        FoodItem(
            id = "6",
            name = "Veggie Burger",
            description = "Plant-based patty with fresh vegetables",
            price = 11.99,
            category = "Burgers",
            isVegetarian = true,
            restaurantId = "2"
        )
    )
    
    private val sushiItems = listOf(
        FoodItem(
            id = "7",
            name = "California Roll",
            description = "Crab, avocado, and cucumber",
            price = 8.99,
            category = "Sushi",
            restaurantId = "3"
        ),
        FoodItem(
            id = "8",
            name = "Salmon Nigiri",
            description = "Fresh salmon over pressed rice",
            price = 12.99,
            category = "Sushi",
            restaurantId = "3"
        ),
        FoodItem(
            id = "9",
            name = "Vegetable Roll",
            description = "Assorted fresh vegetables in sushi rice",
            price = 7.99,
            category = "Sushi",
            isVegetarian = true,
            restaurantId = "3"
        )
    )
    
    val restaurants = listOf(
        Restaurant(
            id = "1",
            name = "Pizza Paradise",
            description = "Authentic Italian pizzas made with love",
            rating = 4.5f,
            deliveryTime = "25-35 min",
            cuisineType = "Italian",
            menu = pizzaItems
        ),
        Restaurant(
            id = "2",
            name = "Burger House",
            description = "Gourmet burgers and sides",
            rating = 4.3f,
            deliveryTime = "20-30 min",
            cuisineType = "American",
            menu = burgerItems
        ),
        Restaurant(
            id = "3",
            name = "Sushi Station",
            description = "Fresh sushi and Japanese cuisine",
            rating = 4.7f,
            deliveryTime = "30-40 min",
            cuisineType = "Japanese",
            menu = sushiItems
        ),
        Restaurant(
            id = "4",
            name = "Taco Fiesta",
            description = "Authentic Mexican street food",
            rating = 4.4f,
            deliveryTime = "15-25 min",
            cuisineType = "Mexican",
            menu = emptyList()
        )
    )
}
