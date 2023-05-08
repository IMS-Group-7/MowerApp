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


@Composable
fun SessionHistoryPage() {
    val sessionList = remember { mutableStateListOf<Session>() }

    Column {
        Button(
            onClick = {
                // Add a new case of a session
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

        // Display Sessions
        for (session in sessionList) {
            ClickableText(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Session: ${session.sessionNumber}")
                    }
                    append("\nStart time: ${session.startTime}")
                    append("\nEnd time: ${session.endTime}")
                },
                onClick = {
                    // Handle the onClick event for the session case
                    // Implement the logic to navigate to the summary page
                }
            )
        }
    }
}

@Composable
fun SessionCase(session: Session, onClick: () -> Unit) {
    // Display Session case with information
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
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