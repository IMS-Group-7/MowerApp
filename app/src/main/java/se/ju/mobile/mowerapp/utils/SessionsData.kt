package se.ju.mobile.mowerapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import se.ju.mobile.mowerapp.views.Screen
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SessionsData(navController: NavController) {
    val sessionsList = ArrayList<Session>()
    val res = ApiManager()

    runBlocking {
        val asyncResponse = res.makeHttpGetRequest("http://34.173.248.99/sessions")
        val jsonResponse = JSONArray(asyncResponse)
        for (i in 0 until jsonResponse.length()) {
            sessionsList.add(
                Session(
                    title = jsonResponse.getJSONObject(i).getString("id"),
                    startTime = jsonResponse.getJSONObject(i).getString("startTime").toString(),
                    endTime = jsonResponse.getJSONObject(i).getString("endTime").toString(),
                )
            )
        }
    }
    for (i in 0 until sessionsList.size) {
        SessionCase(session = sessionsList[i], navController = navController)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SessionCase(session: Session, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(16.dp, 16.dp, 16.dp, 0.dp)
            .border(1.dp, Color.Black)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                navController.navigate("sessionSummary/${session.title}")
            },
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text("ID: ${session.title}")
            Text("Start time: ${formatStringtoDate(session.startTime)}")
            Text("End time: ${formatStringtoDate(session.endTime)}")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatStringtoDate(rawDate: String): String {
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale("US"))
    val formatter = SimpleDateFormat("MM/dd/yyyy - HH:mm:ss", Locale("US"))

    var date = Date()

    try {
        date = parser.parse(rawDate)!!
    } catch (e: ParseException) {
        return "In progress..."
    }
    return formatter.format(date)
}

data class Session(
    val title: String,
    val startTime: String,
    val endTime: String,
)