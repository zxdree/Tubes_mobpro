package com.adre0089.tubes_mobpro.navigation

sealed class Screen(val route: String) {
    data object  Home: Screen("mainScreen")
    data object  About: Screen("aboutScreen")
}