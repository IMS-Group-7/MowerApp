package se.ju.mobile.mowerapp.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.material.Scaffold
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import se.ju.mobile.mowerapp.utils.NavBar
import se.ju.mobile.mowerapp.utils.SessionsData


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SessionHistoryScreen(navController: NavController) {

    Column(modifier = Modifier
        .padding(0.dp)
        .background(Color(0xFF273A60))) {
        Text(
            text = "Session History",
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 24.sp,
            color = Color.White
        )

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = { NavBar(navController = navController) },
            content = { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Sessions display
                        SessionsData(navController)
                    }
                }
            }
        )
    }
}