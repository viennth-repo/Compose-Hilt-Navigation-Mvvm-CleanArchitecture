package com.viennth.app.demo.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.viennth.app.demo.presentation.ui.screen.comingsoon.ComingSoonScreen
import com.viennth.app.demo.presentation.ui.screen.favorite.FavoriteScreen
import com.viennth.app.demo.presentation.ui.screen.home.HomeScreen
import com.viennth.app.demo.presentation.ui.screen.login.LoginScreen
import com.viennth.app.demo.presentation.ui.screen.mainscreen.MainScreen
import com.viennth.app.demo.presentation.ui.screen.profile.ProfileScreen


@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Route.LoginScreen.screenName,
        route = "ROOT"
    ) {
        composable(route = Route.MainTab.screenName) {
            MainTabNavigation()
        }
        composable(route = Route.LoginScreen.screenName) {
            LoginScreen(navController)
        }
        composable(route = Route.ComingSoonScreen.screenName) {
            ComingSoonScreen()
        }
    }
}

@Composable
fun MainTabNavigation(
    navController: NavHostController = rememberNavController(),
) {
    MainScreen(navController = navController) {
        NavHost(
            navController = navController,
            startDestination = Route.MainTab.screenName
        ) {
            mainTabGraph(navController)
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
