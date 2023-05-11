package se.ju.mobile.mowerapp

import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem



@Composable
fun NavBar(navController: NavController) {
    BottomNavigation(
        modifier = Modifier.height(56.dp),
        backgroundColor = Color.Blue
    ) {
        BottomNavigationItem(
            selected = navController.currentDestination?.route == "moving",
            onClick = { navController.navigate("moving") },
            icon = { /* Icône de l'item */ },
            label = { "Moving" }
        )
        BottomNavigationItem(
            selected = navController.currentDestination?.route == "session",
            onClick = { navController.navigate("session") },
            icon = { /* Icône de l'item */ },
            label = { "Resume" }
        )
    }
}

