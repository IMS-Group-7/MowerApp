package se.ju.mobile.mowerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import se.ju.mobile.mowerapp.ui.theme.MowerAppTheme
import android.util.Log
import se.ju.mobile.mowerapp.utils.Navigation
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