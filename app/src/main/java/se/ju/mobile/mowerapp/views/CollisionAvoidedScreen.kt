package se.ju.mobile.mowerapp.views

import android.os.Build
import androidx.annotation.RequiresApi
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
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import se.ju.mobile.mowerapp.utils.ApiManager
import se.ju.mobile.mowerapp.utils.Collision
import se.ju.mobile.mowerapp.utils.NavBar
import se.ju.mobile.mowerapp.utils.PathView
import se.ju.mobile.mowerapp.utils.formatStringtoDate
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CollisionAvoidedScreen(sessionId: String, collisionId: String, navController: NavController) {
    var obstacle: Obstacle? = null
    var collision: Collision? = null
    val res = ApiManager()

    runBlocking {
        val asyncResponse = res.makeHttpGetRequest("http://34.173.248.99/sessions/${sessionId}")
        val jsonResponse = JSONObject(asyncResponse).getJSONArray("coordinate")
        for (i in 0 until jsonResponse.length()) {
            var str = jsonResponse.getJSONObject(i).getJSONObject("obstacle").getString("coordinateId")
            var compare = collisionId.compareTo(str)
            if (compare == 0) {
                collision = Collision (
                    title = "",
                    id = jsonResponse.getJSONObject(i).getString("id"),
                    coordinates = Pair(jsonResponse.getJSONObject(i).getString("x").toFloat(), jsonResponse.getJSONObject(i).getString("y").toFloat()),
                    timestamp = jsonResponse.getJSONObject(i).getString("timestamp"),
                )
                obstacle = Obstacle (
                    id = jsonResponse.getJSONObject(i).getJSONObject("obstacle").getString("id"),
                    coordinateId = jsonResponse.getJSONObject(i).getJSONObject("obstacle").getString("coordinateId"),
                    imagePath = jsonResponse.getJSONObject(i).getJSONObject("obstacle").getString("imagePath"),
                    objectName = jsonResponse.getJSONObject(i).getJSONObject("obstacle").getString("object"),
                )
            }
        }
    }

    Column(modifier = Modifier
        .padding(0.dp)
        .background(Color(0xFF273A60))) {
        Text(
            text = collisionId,
            modifier = Modifier
                .padding(vertical = 25.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 16.sp,
            color = Color.White
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(14.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Zone for the picture of the collision
                Image(
                    painter = rememberAsyncImagePainter(obstacle?.imagePath),
                    contentDescription = null,
                    modifier = Modifier.size(256.dp).padding(vertical = 16.dp)
                )
                Text(
                    text = "Coordinates: x:${collision?.coordinates?.first} - y:${collision?.coordinates?.second}",
                    color = Color.Black
                )
                Text(
                    text = "Timestamp: ${formatStringtoDate(collision?.timestamp.toString())}",
                    color = Color.Black
                )
                Text(
                    text = "Object avoided: ${obstacle?.objectName}",
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color.Black
                )
            }
        }
    }
}

data class Obstacle(
    val id: String,
    val coordinateId: String,
    val imagePath: String,
    val objectName: String,
)