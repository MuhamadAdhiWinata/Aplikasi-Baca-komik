package com.adhi.komik.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object About : Screen("about")
    object Detail : Screen("home/{komikId}") {
        fun createRoute(komikId: Int) = "home/$komikId"
    }
}
