package se.ju.mobile.mowerapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import org.json.JSONObject
import se.ju.mobile.mowerapp.views.Screen
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SessionDetails(sessionId: String, navController: NavController) {
    var startTime : String
    var endTime : String
    var collisionAvoided : String
    val collisionsList = ArrayList<Collision>()
    val res = ApiManager()

    runBlocking {
        val asyncResponse = res.makeHttpGetRequest("http://34.173.248.99/sessions/${sessionId}")
        val jsonResponse = JSONObject(asyncResponse).getJSONArray("coordinate")
        val jsonResponseSessionDetails = JSONObject(asyncResponse)
        for (i in 0 until jsonResponse.length()) {
            collisionsList.add(
                Collision(
                    title = "Collision #${i+1}",
                    coordinates = Pair(jsonResponse.getJSONObject(i).getString("x").toFloat(), jsonResponse.getJSONObject(i).getString("y").toFloat()),
                    timestamp = jsonResponse.getJSONObject(i).getString("timestamp"),
                )
            )
        }
        startTime = jsonResponseSessionDetails.getString("startTime")
        endTime = jsonResponseSessionDetails.getString("endTime")
        collisionAvoided = jsonResponseSessionDetails.getInt("obstacleCount").toString()
    }
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SessionDetailsDisplay(startTime, endTime, collisionAvoided)
        for (i in 0 until collisionsList.size) {
            CollisionsListComposable(collision = collisionsList[i], navController = navController)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SessionDetailsDisplay(startTime : String, endTime : String, collisionsAvoided : String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 12.dp, 0.dp, 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Start Time: ${formatStringtoDate(startTime)}",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                fontSize = 14.sp,
            )
            Text(
                text = "End Time: ${formatStringtoDate(endTime)}",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                fontSize = 14.sp,
            )
            Text(
                text = "Number of collisions avoided: $collisionsAvoided",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                fontSize = 14.sp,
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CollisionsListComposable(collision: Collision, navController: NavController) {
    Card(
        modifier = Modifier
            .border(1.dp, Color.Black)
            .padding(16.dp, 16.dp, 16.dp, 0.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { navController.navigate(Screen.CollisionAvoidedScreen.route) },
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(collision.title)
            Text("Coordinates: x:${collision.coordinates.first} - y:${collision.coordinates.second}")
            Text("Timestamp: ${formatStringtoDate(collision.timestamp)}")
        }
    }
}

data class Collision(
    val title: String,
    val coordinates: Pair<Float, Float>,
    val timestamp: String,
)

data class SummarySession(
    val title: String,
    val startTime: String,
    val endTime: String,
    val numberOfCollisions: Int
)