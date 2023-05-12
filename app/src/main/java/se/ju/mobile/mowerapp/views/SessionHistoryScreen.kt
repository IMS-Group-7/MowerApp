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
import androidx.navigation.NavController
import se.ju.mobile.mowerapp.utils.NavBar


@Composable
fun SessionHistoryScreen(navController: NavController) {
    val sessionList = remember { mutableStateListOf<Session>() }

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
                        Button(
                            onClick = {
                                // Ajouter une nouvelle session
                                val newSession = Session(
                                    title = "Session",
                                    sessionNumber = sessionList.size + 1,
                                    startTime = LocalDateTime.now(),
                                    endTime = LocalDateTime.now().plusHours(1)
                                )
                                sessionList.add(newSession)
                            }
                        ) {
                            Text("New Session")
                        }

                        // Afficher les sessions
                        for (session in sessionList) {
                            SessionCase(session = session, navController = navController)
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun SessionCase(session: Session, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .border(1.dp, Color.Black)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                navController.navigate(Screen.SessionSummaryScreen.route)
            },
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Session: ${session.sessionNumber}")
            Text("Start time: ${session.startTime}")
            Text("End time: ${session.endTime}")
        }
    }
}

data class Session(
    val title: String,
    val sessionNumber: Int,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime
)
