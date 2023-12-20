package com.dicoding.warceng.ui.navigation

sealed class Screen(val route: String){
    object Home: Screen("home")
    object Favorite: Screen("favorite")
    object Profile: Screen("profile")
    object Category: Screen("category/{typeCategory}"){
        fun createRoute(typeCategory: String) = "category/$typeCategory"
    }
    object DetailUmkm: Screen("home/{umkmId}"){
        fun createRoute(umkmId: Long) = "home/$umkmId"
    }
}