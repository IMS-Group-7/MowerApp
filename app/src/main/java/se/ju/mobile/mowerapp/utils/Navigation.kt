package se.ju.mobile.mowerapp.utils
import android.os.Build
import androidx.annotation.RequiresApi
import se.ju.mobile.mowerapp.R

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import androidx.navigation.NavType
import androidx.navigation.navArgument
import kotlinx.coroutines.MainScope
import se.ju.mobile.mowerapp.socket.SocketManager
import se.ju.mobile.mowerapp.views.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.DrivingScreen.route) {
        composable(route = Screen.DrivingScreen.route) {
            DrivingScreen(socketManager = SocketManager(), navController = navController)
        }
        composable(route = Screen.SessionHistoryScreen.route) {
            SessionHistoryScreen(navController = navController)
        }
        composable(route = "sessionSummary/{sessionId}",
            arguments = listOf(navArgument("sessionId") { defaultValue = "" } )
        ) { backStackEntry ->
            SessionSummaryScreen(sessionId = backStackEntry.arguments?.getString("sessionId")!!, navController = navController)
        }
        composable(route = "sessionSummary/{sessionId}/{collisionId}",
            arguments = listOf(navArgument("sessionId") { defaultValue = "" }, navArgument("collisionId") { defaultValue = "" } )
        ) { backStackEntry ->
            CollisionAvoidedScreen(sessionId = backStackEntry.arguments?.getString("sessionId")!!, collisionId = backStackEntry.arguments?.getString("collisionId")!!, navController = navController)
        }
    }
}

@Composable
fun NavBar(navController: NavController) {
    BottomNavigation(
        modifier = Modifier.height(80.dp)
            .zIndex(zIndex = 10000F),
        backgroundColor = Color(0xFF23252A),
        elevation = 1.dp
    ) {
        BottomNavigationItem(
            selected = navController.currentDestination?.route == Screen.DrivingScreen.route,
            onClick = { navController.navigate(Screen.DrivingScreen.route) },
            icon = { Icon(painterResource(id = R.drawable.mdi_controller), contentDescription = "Driving") },
            label = { Text(text = "Driving") },
            selectedContentColor = Color.White,
            unselectedContentColor = Color(0xFF818181),
            modifier = Modifier.padding(start = 0.dp, top = 12.dp, end = 0.dp, bottom = 16.dp)
        )
        BottomNavigationItem(
            selected = navController.currentDestination?.route == Screen.SessionHistoryScreen.route,
            onClick = { navController.navigate(Screen.SessionHistoryScreen.route) },
            icon = { Icon(painterResource(id = R.drawable.mdi_history), contentDescription = "Driving") },
            label = { Text(text = "History") },
            selectedContentColor = Color.White,
            unselectedContentColor = Color(0xFF818181),
            modifier = Modifier.padding(start = 0.dp, top = 12.dp, end = 0.dp, bottom = 16.dp)
        )
    }
}
