package com.joel.authentication_compose.view

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavGraph(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.AUTHENTICATION_SCREEN )
    {
        composable(route = Routes.AUTHENTICATION_SCREEN){
            AuthenticationScreen()
        }
        composable(route = Routes.REGISTER_SCREEN){
            SignInScreen()
        }
        composable(route = Routes.LOG_IN_SCREEN){
            LogInScreen()
        }
        composable(route = Routes.DETAILS_SCREEN){
            DetailsScreen()
        }

    }
}