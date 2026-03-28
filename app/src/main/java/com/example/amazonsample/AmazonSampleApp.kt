package com.example.amazonsample

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.amazonsample.navigation.NavGraph

@HiltAndroidApp
class AmazonSampleApp : Application()

@Composable
fun AmazonSampleAppContent() { // Renamed to avoid conflict with Application class name
    val navController = rememberNavController()
    NavGraph(navController = navController)
} 