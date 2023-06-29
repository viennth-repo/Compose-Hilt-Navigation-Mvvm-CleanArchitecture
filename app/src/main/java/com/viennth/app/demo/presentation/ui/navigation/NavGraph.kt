package com.viennth.app.demo.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.viennth.app.demo.R
import com.viennth.app.demo.presentation.ui.screen.favorite.FavoriteScreen
import com.viennth.app.demo.presentation.ui.screen.home.HomeScreen
import com.viennth.app.demo.presentation.ui.screen.profile.ProfileScreen


@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = Route.MainTab.screenName
    ) {
        composable(route = Route.LoginScreen.screenName) {
        }
        mainTabGraph(navController)
        composable(route = Route.DetailsScreen.screenName) {

        }
        composable(route = Route.ComingSoonScreen.screenName) {

        }
    }
}

fun NavGraphBuilder.mainTabGraph(navController: NavController) {
    navigation(
        startDestination = Route.HomeScreen.screenName,
        route = Route.MainTab.screenName
    ) {
        composable(route = Route.HomeScreen.screenName) {
            HomeScreen()
        }
        composable(route = Route.FavoriteScreen.screenName) {
            FavoriteScreen()
        }
        composable(route = Route.ProfileScreen.screenName) {
            ProfileScreen()
        }
    }
}

@Composable
fun navigationTitle(navController: NavController): String {
    return when (currentRoute(navController)) {
        Screen.Details.route -> stringResource(id = R.string.title_details)
        Screen.ComingSoon.route -> stringResource(id = R.string.title_coming_soon)
        Screen.Login.route -> stringResource(id = R.string.title_login)
        else -> {
            ""
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route?.substringBeforeLast("/")
}
