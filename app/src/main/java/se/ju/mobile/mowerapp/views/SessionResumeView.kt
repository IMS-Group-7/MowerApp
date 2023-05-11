package se.ju.mobile.mowerapp.views

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import se.ju.mobile.mowerapp.utils.NavBar
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun SessionSummaryScreen(navController: NavController) {
    val sessionList = remember { mutableStateListOf<SummarySession>() }

    Column(modifier = Modifier
        .padding(0.dp)
        .background(Color(0xFF273A60))) {
        Text(
            text = "Name of the Actual Session",
            modifier = Modifier
                .padding(vertical = 25.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 24.sp,
            color = Color.White
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
                    text = "Start Time: 11/05/2023 - 17:18:23",
                )
                Text(
                    text = "End Time: 11/05/2023 - 17:18:24",
                )
                Text(
                    text = "Number of collisions avoided: 02",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }

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
                        Button(
                            onClick = {
                                // Ajouter une nouvelle session
                                val newSession = SummarySession(
                                    title = "Session",
                                    sessionNumber = sessionList.size + 1,
                                    Date = LocalDate.now(),
                                    Time = LocalTime.now().plusHours(1),
                                    NumberOfCollisions = 2
                                )
                                sessionList.add(newSession)
                            }
                        ) {
                            Text("New Collision")
                        }

                        // Afficher les sessions
                        for (session in sessionList) {
                            SessionResume(session = session, navController = navController)
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun SessionResume(session: SummarySession, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .border(1.dp, Color.Black)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { navController.navigate(Screen.CollisionAvoidedScreen.route) },
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Collision avoided #${session.sessionNumber}")
            Text("Coordinates: 123.312")
            Text("TimeStamp: ${formatDate(session.Date)} - ${formatTime(session.Time)}")
        }
    }
}

fun formatDate(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return date.format(formatter)
}

fun formatTime(time: LocalTime): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    return time.format(formatter)
}

data class SummarySession(
    val title: String,
    val sessionNumber: Int,
    val Date: LocalDate,
    val Time: LocalTime,
    val NumberOfCollisions: Int,
)