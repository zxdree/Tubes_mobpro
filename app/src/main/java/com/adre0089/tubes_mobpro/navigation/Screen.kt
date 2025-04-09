package com.adre0089.tubes_mobpro.navigation

sealed class Screen(val route: String) {
    data object  Home: Screen("mainScreen")
    data object  About: Screen("aboutScreen")
    data object  main_home: Screen("mainHomeScreen")
    data object  Kalaog_kemeja: Screen("kemeja")
    data object  katalog_Jaket: Screen("jaket")
}