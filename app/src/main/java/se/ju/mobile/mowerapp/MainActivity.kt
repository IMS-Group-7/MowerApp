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

import androidx.navigation.compose.rememberNavController

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import se.ju.mobile.mowerapp.views.ConnectedLawnMower
import androidx.compose.ui.platform.LocalLifecycleOwner
import se.ju.mobile.mowerapp.utils.Navigation
import se.ju.mobile.mowerapp.views.DrivingScreen
import se.ju.mobile.mowerapp.views.Screen
import se.ju.mobile.mowerapp.views.SessionHistoryScreen
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MowerAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    // Screen here
                    Navigation()
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