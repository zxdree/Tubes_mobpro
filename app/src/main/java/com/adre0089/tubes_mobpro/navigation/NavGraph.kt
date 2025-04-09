package com.adre0089.tubes_mobpro.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adre0089.tubes_mobpro.ui.screen.AboutScreen
import com.adre0089.tubes_mobpro.ui.screen.MainScreen
import com.adre0089.tubes_mobpro.ui.screen.PerhitunganUkuranBaju
import com.adre0089.tubes_mobpro.ui.screen.katalogbaju.Jaket
import com.adre0089.tubes_mobpro.ui.screen.katalogbaju.Kemeja
import kotlin.math.round

@Composable
fun SetupNavGraph(navController : NavHostController = rememberNavController()){
    NavHost(
        navController = navController,
        startDestination = Screen.main_home.route

    ){
        composable(route = Screen.main_home.route) {
            PerhitunganUkuranBaju(navController)
        }
        composable(route = Screen.Home.route){
            MainScreen(navController)
        }
        composable(route = Screen.About.route){
            AboutScreen(navController)
        }
        composable(route = Screen.Kalaog_kemeja.route){
            Kemeja(navController)
        }
        composable(route = Screen.katalog_Jaket.route) {
            Jaket(navController)
        }
    }
}