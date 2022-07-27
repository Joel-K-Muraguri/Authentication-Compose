package com.joel.authentication_compose.view

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(
    navController: NavHostController,
    context: Context
){
    NavHost(
        navController = navController ,
        startDestination = Routes.AUTHENTICATION_SCREEN )
    {
        composable(route = Routes.AUTHENTICATION_SCREEN){
            AuthenticationScreen(navController)
        }
        composable(route = Routes.REGISTER_SCREEN){
            SignInScreen(navController,context)
        }
        composable(route = Routes.LOG_IN_SCREEN){
            LogInScreen(navController,context)
        }
        composable(route = Routes.DETAILS_SCREEN){
            DetailsScreen(context)
        }

    }
}