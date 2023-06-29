package com.viennth.app.demo.presentation.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.viennth.app.demo.R
import com.viennth.app.demo.presentation.ui.theme.BOTTOM_NAV_ICON_END_PADDING
import com.viennth.app.demo.presentation.ui.theme.BOTTOM_NAV_ICON_OFFSET_X

sealed class Screen(
    val route: String,
    @StringRes val title: Int = R.string.app_name,
    val navIcon: (@Composable () -> Unit) = {
        Icon(
            Icons.Filled.Home, contentDescription = "home"
        )
    }
) {

    object Login : Screen(Route.LoginScreen.screenName)
    object Home : Screen(Route.HomeScreen.screenName)
    object Favorite : Screen(Route.FavoriteScreen.screenName)
    object Profile : Screen(Route.ProfileScreen.screenName)

    //Bottom Navigation
    object BottomNavHome: Screen(
        route = Route.HomeScreen.screenName,
        title = R.string.bottom_nav_home,
        navIcon = {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "home",
                modifier = Modifier
                    .padding(end = BOTTOM_NAV_ICON_END_PADDING)
                    .offset(x = BOTTOM_NAV_ICON_OFFSET_X)
            )
        }
    )

    object BottomNavFavorite: Screen(
        route = Route.FavoriteScreen.screenName,
        title = R.string.bottom_nav_favorite,
        navIcon = {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "favorite",
                modifier = Modifier
                    .padding(end = BOTTOM_NAV_ICON_END_PADDING)
                    .offset(x = BOTTOM_NAV_ICON_OFFSET_X)
            )
        }
    )

    object BottomNavProfile: Screen(
        route = Route.ProfileScreen.screenName,
        title = R.string.bottom_nav_profile,
        navIcon = {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "profile",
                modifier = Modifier
                    .padding(end = BOTTOM_NAV_ICON_END_PADDING)
                    .offset(x = BOTTOM_NAV_ICON_OFFSET_X)
            )
        }
    )


    object Details : Screen(
        route = Route.DetailsScreen.screenName
    )

    object ComingSoon : Screen(
        route = Route.ComingSoonScreen.screenName
    )
}

enum class Route(val screenName: String) {
    MainTab("MainTab"),
    LoginScreen("LoginScreen"),
    HomeScreen("HomeScreen"),
    FavoriteScreen("FavoriteScreen"),
    ProfileScreen("ProfileScreen"),
    DetailsScreen("DetailsScreen"),
    ComingSoonScreen("ComingSoonScreen")
}