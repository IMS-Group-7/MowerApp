package se.ju.mobile.mowerapp.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import se.ju.mobile.mowerapp.utils.SessionDetails

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SessionSummaryScreen(sessionId: String, navController: NavController) {
    Column(modifier = Modifier
        .padding(0.dp)
        .background(Color(0xFF273A60))) {
        Text(
            text = sessionId,
            modifier = Modifier
                .padding(vertical = 25.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            color = Color.White
        )

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            content = { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    // Displays the screen data
                    SessionDetails(sessionId = sessionId, navController = navController)
                }
            }
        )
    }
}