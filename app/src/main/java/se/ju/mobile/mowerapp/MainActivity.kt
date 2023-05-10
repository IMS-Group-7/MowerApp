package se.ju.mobile.mowerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import se.ju.mobile.mowerapp.ui.theme.MowerAppTheme
import se.ju.mobile.mowerapp.views.LawnMowerConnectionView
import se.ju.mobile.mowerapp.views.MovingRobotArrow
import android.content.Intent
import androidx.constraintlayout.compose.ConstraintLayout
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import se.ju.mobile.mowerapp.views.ConnectedLawnMower
import androidx.compose.ui.platform.LocalLifecycleOwner
import se.ju.mobile.mowerapp.views.SessionHistoryPage
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


@Composable
fun LawnMowerContent(onConnect: () -> Unit) {
    val connected = remember { mutableStateOf(false) }
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (logo, content, button) = createRefs()

        Image(
            painter = painterResource(R.drawable.husqvarna_logo),
            contentDescription = "Husqvarna logo",
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(parent.top, margin = 64.dp)
                    centerHorizontallyTo(parent)
                }
                .size(220.dp)

        )


        Box(
            modifier = Modifier.constrainAs(content) {
                top.linkTo(button.bottom, margin = 100.dp)

                centerHorizontallyTo(parent)
            }
        ) {
            LawnMowerConnectionScreen(connected, onConnect)
        }

        Box(
            modifier = Modifier.constrainAs(button) {
                bottom.linkTo(content.top, margin = 10.dp)
                top.linkTo(parent.top, margin = 1.dp)
                centerHorizontallyTo(parent)
            }
        ) {
        }
    }
}

@Composable
fun LawnMowerConnectionScreen(connected: MutableState<Boolean>, onConnect: () -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    if (connected.value) {
        ConnectedLawnMower()
    } else {
        LawnMowerConnectionView {
            lifecycleOwner.lifecycleScope.launch {
                delay(2000)
                connected.value = true
                onConnect()
            }
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MowerAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    LawnMowerContent(onConnect = {
                        val intent = Intent(this@MainActivity, MovingRobotArrowActivity::class.java)
                        startActivity(intent)

                        // onConnect callback
                    })
                }
            }
        }
        fetchData()
    }

    private fun fetchData() {
        Thread {
            try {
                val url = URL("http://10.0.2.2:5000/api/mower")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val response = reader.readText()
                    reader.close()

                    // Handle the data here, e.g., update the UI
                    runOnUiThread {
                        // Update the UI with the fetched data
                    }
                } else {
                    // Handle the error
                    Log.e("MainActivity", "Error response code: $responseCode")
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Error: ${e.message}")
                e.printStackTrace()
            }
        }.start()
    }
}

class MovingRobotArrowActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MowerAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MovingRobotArrowContent()
                }
            }
        }
    }
}

@Composable
fun MovingRobotArrowContent() {
    val navController = rememberNavController()
    SessionHistoryPage(navController = navController)
}

