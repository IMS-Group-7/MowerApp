package se.ju.mobile.mowerapp.views

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime
import androidx.compose.material.Card
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.material.Scaffold
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import se.ju.mobile.mowerapp.utils.NavBar
import se.ju.mobile.mowerapp.utils.PathView
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Composable
fun CollisionAvoidedScreen(navController: NavController) {

    Column(modifier = Modifier
        .padding(0.dp)
        .background(Color(0xFF273A60))) {
        Text(
            text = "Name of the Collision #Number",
            modifier = Modifier
                .padding(vertical = 25.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 24.sp,
            color = Color.White
        )

        // Zone for the picture of the collision
        AndroidView (
            modifier = Modifier
                .padding(0.dp)
                .fillMaxWidth()
                .height(245.dp)
                .background(color = Color.White),
            factory = { context ->
                PathView(context).apply {  }
            }
        )

        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .background(Color.White)
                .height(120.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(14.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Coordinates: 123.312",
                )
                Text(
                    text = "TimeStamp: 11/05/2023 - 17:27:43",
                )
                Text(
                    text = "Object avoided: Pen",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

/*fun formatDate(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return date.format(formatter)
}

fun formatTime(time: LocalTime): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    return time.format(formatter)
}*/

data class CollisionSession(
    val title: String,
    val sessionNumber: Int,
    val Date: LocalDate,
    val Time: LocalTime,
    val NumberOfCollisions: Int,
)