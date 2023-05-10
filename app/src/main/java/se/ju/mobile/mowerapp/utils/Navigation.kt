package se.ju.mobile.mowerapp.utils

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import se.ju.mobile.mowerapp.views.Screen

import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.currentCompositionLocalContext
import se.ju.mobile.mowerapp.views.DrivingScreen
import se.ju.mobile.mowerapp.views.SessionHistoryScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.DrivingScreen.route) {
        composable(route = Screen.DrivingScreen.route) {
            DrivingScreen(coroutineScope = , navController = navController)
        }
        composable(route = Screen.SessionHistoryScreen.route) {
            SessionHistoryScreen(navController = navController)
        }
    }
}

@Composable
fun NavBar(navController: NavController) {
    BottomNavigation(
        modifier = Modifier.height(56.dp),
        backgroundColor = Color.Blue
    ) {
        BottomNavigationItem(
            selected = navController.currentDestination?.route == Screen.DrivingScreen.route,
            onClick = { navController.navigate(Screen.DrivingScreen.route) },
            icon = { /* Icône de l'item */ },
            label = { "Driving" }
        )
        BottomNavigationItem(
            selected = navController.currentDestination?.route == Screen.SessionHistoryScreen.route,
            onClick = { navController.navigate(Screen.SessionHistoryScreen.route) },
            icon = { /* Icône de l'item */ },
            label = { "History" }
        )
    }
}
