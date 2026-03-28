package com.example.amazonsample.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.amazonsample.feature.auth.navigation.AuthNavigation
import com.example.amazonsample.feature.auth.navigation.AuthScreen
import com.example.amazonsample.feature.home.presentation.HomeScreen
import com.example.amazonsample.feature.product.presentation.ProductDetailScreen
import com.example.amazonsample.feature.cart.presentation.CartScreen
import com.example.amazonsample.feature.orders.presentation.OrdersScreen
import com.example.amazonsample.feature.wishlist.presentation.WishlistScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ProductDetail : Screen("product/{productId}") {
        fun createRoute(productId: String) = "product/$productId"
    }
    object Cart : Screen("cart")
    object Orders : Screen("orders")
    object Wishlist : Screen("wishlist")
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AuthScreen.Login.route
    ) {
        AuthNavigation(
            onAuthSuccess = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(AuthScreen.Login.route) { inclusive = true }
                }
            }
        )
        
        composable(Screen.Home.route) {
            HomeScreen(
                onProductClick = { productId ->
                    navController.navigate(Screen.ProductDetail.createRoute(productId))
                },
                onCartClick = {
                    navController.navigate(Screen.Cart.route)
                },
                onOrdersClick = {
                    navController.navigate(Screen.Orders.route)
                },
                onWishlistClick = {
                    navController.navigate(Screen.Wishlist.route)
                }
            )
        }
        
        composable(Screen.ProductDetail.route) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailScreen(
                productId = productId,
                onBackClick = {
                    navController.popBackStack()
                },
                onAddToCart = {
                    navController.navigate(Screen.Cart.route)
                }
            )
        }
        
        composable(Screen.Cart.route) {
            CartScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onCheckout = {
                    navController.navigate(Screen.Orders.route)
                }
            )
        }
        
        composable(Screen.Orders.route) {
            OrdersScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.Wishlist.route) {
            WishlistScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onProductClick = { productId ->
                    navController.navigate(Screen.ProductDetail.createRoute(productId))
                }
            )
        }
    }
} 