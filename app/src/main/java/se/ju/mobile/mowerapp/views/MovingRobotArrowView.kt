package se.ju.mobile.mowerapp.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.material.ButtonDefaults
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.viewinterop.AndroidView
import se.ju.mobile.mowerapp.ui.theme.MowerAppTheme
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import se.ju.mobile.mowerapp.utils.PathView

fun fetchData(coroutineScope: CoroutineScope, arrowDirection: String, showDialog: MutableState<Boolean>, dialogTitle: MutableState<String>, dialogMessage: MutableState<String>) {

    coroutineScope.launch(Dispatchers.IO) {
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

                // Update the UI with the fetched data
                coroutineScope.launch(Dispatchers.Main) {
                    showDialog.value = true
                    dialogTitle.value = "Arrow pressed: $arrowDirection" // Update this line
                    dialogMessage.value = "Successfully fetched data from mockup_server. The fetched data is: $response" // Update this line
                }
            } else {
                // Handle the error
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

@Composable
fun ShowAlertDialog(showDialog: MutableState<Boolean>, dialogTitle: MutableState<String>, dialogMessage: MutableState<String>) {
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = dialogTitle.value) }, // Update this line
            text = { Text(text = dialogMessage.value) }, // Update this line
            confirmButton = {
                TextButton(onClick = { showDialog.value = false }) {
                    Text("OK")
                }
            }
        )
    }
}



@Composable
fun MovingRobotArrow(coroutineScope: CoroutineScope) {
    var isStarted by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }
    val showDialog = remember { mutableStateOf(false) }
    val dialogTitle = remember { mutableStateOf("") }
    val dialogMessage = remember { mutableStateOf("") }

    MowerAppTheme {
        Column(modifier = Modifier.padding(0.dp) .background(Color(0xFF273A60))) {
            Text(
                text = "Dashboard",
                modifier = Modifier.padding(vertical = 16.dp)
                    .align(Alignment.CenterHorizontally),
                fontSize = 24.sp,
                color = Color.White
            )

            // Second zone for the map
             AndroidView (
                modifier = Modifier.padding(0.dp)
                    .fillMaxWidth()
                    .height(252.dp)
                    .background(color = Color.White),
                factory = { context ->
                    PathView(context).apply {  }
                }
            )

            // Third zone : 4 buttons for arrows
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(modifier = Modifier.size(50.dp)) {
                        Button(
                            onClick = {fetchData(coroutineScope, "Left", showDialog, dialogTitle, dialogMessage) },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF273A60), contentColor = Color.White),
                            shape = CircleShape,
                            border = BorderStroke(1.dp, Color.White),
                            modifier = Modifier.fillMaxSize(),
                            enabled = isStarted
                        ) {
                            Text("Q", style = TextStyle(fontSize = 20.sp), textAlign = TextAlign.Center)
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(80.dp)
                    ) {
                        Box(modifier = Modifier.size(50.dp)) {
                            Button(
                                onClick = {fetchData(coroutineScope, "Up", showDialog, dialogTitle, dialogMessage) },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF273A60), contentColor = Color.White),
                                shape = CircleShape,
                                border = BorderStroke(1.dp, Color.White),
                                modifier = Modifier.fillMaxSize(),
                                enabled = isStarted
                            ) {
                                Text("Z", style = TextStyle(fontSize = 20.sp), textAlign = TextAlign.Center)
                            }
                        }

                        Box(modifier = Modifier.size(50.dp)) {
                            Button(
                                onClick = { fetchData(coroutineScope, "Down", showDialog, dialogTitle, dialogMessage) },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF273A60), contentColor = Color.White),
                                shape = CircleShape,
                                border = BorderStroke(1.dp, Color.White),
                                modifier = Modifier.fillMaxSize(),
                                enabled = isStarted
                            ) {
                                Text("S", style = TextStyle(fontSize = 20.sp), textAlign = TextAlign.Center)
                            }
                        }
                    }
                    Box(modifier = Modifier.size(50.dp)) {
                        Button(
                            onClick = { fetchData(coroutineScope, "Right", showDialog, dialogTitle, dialogMessage)},
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF273A60), contentColor = Color.White),
                            shape = CircleShape,
                            border = BorderStroke(1.dp, Color.White),
                            modifier = Modifier.fillMaxSize(),
                            enabled = isStarted
                        ) {
                            Text("D", style = TextStyle(fontSize = 20.sp), textAlign = TextAlign.Center)
                        }
                    }
                }
            }

            // Add buttons Start and Stop
            Row(
                modifier = Modifier.fillMaxWidth() .padding(bottom = 35.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(
                    onClick = {
                        expanded = !expanded
                        isStarted = true
                    },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF273A60), contentColor = Color.White),
                    border = BorderStroke(1.dp, Color.White),
                    enabled = isStarted
                ) {
                    Text(text = "A", color = Color.White)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.width(IntrinsicSize.Min)
                ) {
                    DropdownMenuItem(onClick = {
                        selectedOption = "Automatic Driving"
                        expanded = false
                        isStarted = false
                    }) {
                        Text(text = "Automatic Driving")
                    }
                    DropdownMenuItem(onClick = {
                        selectedOption = "Manual Driving"
                        expanded = false
                        isStarted = true
                    }) {
                        Text(text = "Manual Driving")
                    }
                }
                Button(
                    onClick = { isStarted = true },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF273A60), contentColor = Color.White),
                    border = BorderStroke(1.dp, Color.White),
                    enabled = !isStarted
                ) {
                    Text("Start")
                }

                Button(
                    onClick = { isStarted = false },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF273A60), contentColor = Color.White),
                    border = BorderStroke(1.dp, Color.White),
                    enabled = isStarted
                ) {
                    Text("Stop")
                }
            }
        }
    }
    ShowAlertDialog(showDialog, dialogTitle, dialogMessage)
}