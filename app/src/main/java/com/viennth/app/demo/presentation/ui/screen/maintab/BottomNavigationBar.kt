package com.viennth.app.demo.presentation.ui.screen.maintab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.viennth.app.demo.presentation.ui.navigation.MainTabNavigation
import com.viennth.app.demo.presentation.ui.navigation.Screen
import com.viennth.app.demo.presentation.ui.navigation.currentRoute
import com.viennth.app.demo.presentation.ui.theme.BottomTabColor

@Composable
fun MainBottomBar(navController: NavController) {
    BottomNavigation(backgroundColor = BottomTabColor) {
        listOf(
            Screen.BottomNavHome,
            Screen.BottomNavFavorite,
            Screen.BottomNavProfile
        ).forEach { item ->
            BottomNavigationItem(
                label = { Text(text = stringResource(id =  item.title)) },
                selected = currentRoute(navController) == item.route,
                icon = item.navIcon,
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                })
        }
    }
}
