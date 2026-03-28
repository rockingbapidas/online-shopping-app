package com.example.amazonsample.feature.auth.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.amazonsample.feature.auth.presentation.login.LoginScreen

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen("auth/login")
}

fun NavGraphBuilder.AuthNavigation(onAuthSuccess: () -> Unit) {
    composable(AuthScreen.Login.route) {
        LoginScreen(
            onLoginSuccess = onAuthSuccess
        )
    }
}

@Composable
fun AuthNavGraph(startDestination: String = AuthScreen.Login.route, onAuthSuccess: () -> Unit = {}) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        AuthNavigation(onAuthSuccess)
    }
} 