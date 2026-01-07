package com.fooddelivery.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fooddelivery.app.ui.navigation.Screen
import com.fooddelivery.app.ui.screens.*
import com.fooddelivery.app.ui.theme.FoodDeliveryAppTheme
import com.fooddelivery.app.viewmodel.FoodViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodDeliveryAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FoodDeliveryApp()
                }
            }
        }
    }
}

@Composable
fun FoodDeliveryApp() {
    val navController = rememberNavController()
    val viewModel: FoodViewModel = viewModel()
    
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                onRestaurantClick = { restaurantId ->
                    viewModel.selectRestaurant(restaurantId)
                    navController.navigate(Screen.RestaurantDetail.createRoute(restaurantId))
                },
                onCartClick = {
                    navController.navigate(Screen.Cart.route)
                }
            )
        }
        
        composable(Screen.RestaurantDetail.route) {
            RestaurantDetailScreen(
                viewModel = viewModel,
                onBackClick = { navController.navigateUp() },
                onCartClick = {
                    navController.navigate(Screen.Cart.route)
                }
            )
        }
        
        composable(Screen.Cart.route) {
            CartScreen(
                viewModel = viewModel,
                onBackClick = { navController.navigateUp() },
                onCheckoutClick = {
                    navController.navigate(Screen.Checkout.route)
                }
            )
        }
        
        composable(Screen.Checkout.route) {
            CheckoutScreen(
                viewModel = viewModel,
                onBackClick = { navController.navigateUp() },
                onOrderPlaced = {
                    navController.navigate(Screen.OrderConfirmation.route) {
                        popUpTo(Screen.Home.route)
                    }
                }
            )
        }
        
        composable(Screen.OrderConfirmation.route) {
            OrderConfirmationScreen(
                onBackToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
