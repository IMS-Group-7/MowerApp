package se.ju.mobile.mowerapp.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime
import androidx.compose.material.Card
import androidx.compose.foundation.text.ClickableText
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.foundation.border
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.clickable


@Composable
fun SessionHistoryPage() {
    val sessionList = remember { mutableStateListOf<Session>() }

    Column(
        modifier = Modifier.fillMaxSize(),
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
            SessionCase(session = session)
        }
    }
}

@Composable
fun SessionCase(session: Session) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .border(1.dp, Color.Black)
            .fillMaxWidth()
            .wrapContentHeight(),
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
