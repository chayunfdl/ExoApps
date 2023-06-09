package com.chayun.jetpackcompose.exoapplication.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object About : Screen("about_page")
    object Detail : Screen("home/{exoId}") { fun createRoute(exoId: String) = "home/$exoId" }
}
