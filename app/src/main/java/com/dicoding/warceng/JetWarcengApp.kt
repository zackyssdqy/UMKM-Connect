package com.dicoding.warceng

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.warceng.ui.navigation.NavigationItem
import com.dicoding.warceng.ui.navigation.Screen
import com.dicoding.warceng.ui.screen.favorite.FavoriteScreen
import com.dicoding.warceng.ui.screen.category.CategoryScreen
import com.dicoding.warceng.ui.screen.detail.DetailScreen
import com.dicoding.warceng.ui.screen.home.HomeScreen
import com.dicoding.warceng.ui.screen.profile.ProfileScreen
import com.dicoding.warceng.ui.theme.WarcengAppTheme

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        containerColor = Color.White,
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(id = R.string.home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(id = R.string.favorite),
                icon = Icons.Default.Favorite,
                screen = Screen.Favorite
            ),
            NavigationItem(
                title = stringResource(id = R.string.about),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            ),
        )
        navigationItems.map {item->
            NavigationBarItem(
                label = { Text(text = item.title)},
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route){
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.title)
                }
            )
        }
    }
}

@Composable
fun JetWarcengApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            if(currentRoute == Screen.DetailUmkm.route){

            }else if(currentRoute == Screen.Category.route){

            }else {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { umkmId->
                        navController.navigate(Screen.DetailUmkm.createRoute(umkmId))
                    },
                    navigateToCategory = { categoryType->
                        navController.navigate(Screen.Category.createRoute(categoryType))
                    }
                )
            }

            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(
                route = Screen.DetailUmkm.route,
                arguments = listOf(navArgument("umkmId"){
                    type = NavType.LongType
                })
            ){
                val id = it.arguments?.getLong("umkmId") ?: -1L
                DetailScreen(
                    umkmId = id,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    navigateToDetail = { umkmId->
                        navController.navigate(Screen.DetailUmkm.createRoute(umkmId))
                    },
                )
            }
            composable(
                route = Screen.Category.route,
                arguments = listOf(navArgument("typeCategory"){
                    type = NavType.StringType
                })
            ){
                val typeCategory = it.arguments?.getString("typeCategory") ?: ""
                CategoryScreen(
                    category = typeCategory,
                    navigateBack = {
                        navController.navigateUp()
                    },
                    navigateToDetail = { umkmId->
                        navController.navigate(Screen.DetailUmkm.createRoute(umkmId))
                    },
                )
            }
        }
    }
}

@Preview
@Composable
fun JetWarcengAppPreview() {
    WarcengAppTheme {
        JetWarcengApp()
    }
}