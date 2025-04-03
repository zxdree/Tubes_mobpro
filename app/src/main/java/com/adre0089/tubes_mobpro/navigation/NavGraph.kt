package com.adre0089.tubes_mobpro.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adre0089.tubes_mobpro.ui.screen.AboutScreen
import com.adre0089.tubes_mobpro.ui.screen.MainScreen
import kotlin.math.round

@Composable
fun SetupNavGraph(navController : NavHostController = rememberNavController()){
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route

    ){
        composable(route = Screen.Home.route){
            MainScreen(navController)
        }
        composable(route = Screen.About.route){
            AboutScreen(navController)
        }
    }
}