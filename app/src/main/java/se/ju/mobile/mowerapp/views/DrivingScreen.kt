package se.ju.mobile.mowerapp.views

import android.app.AlertDialog
import android.util.Log
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
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import se.ju.mobile.mowerapp.socket.SocketManager
import se.ju.mobile.mowerapp.utils.NavBar
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import se.ju.mobile.mowerapp.utils.PathView


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
fun DrivingScreen(socketManager: SocketManager, navController: NavController) {
    var isAuto by remember { mutableStateOf(true) }
    var isStarted by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }
    val showDialog = remember { mutableStateOf(false) }
    val dialogTitle = remember { mutableStateOf("") }
    val dialogMessage = remember { mutableStateOf("") }

    socketManager.connectMobileAppToBackend()

    MowerAppTheme {
        Column(modifier = Modifier
            .padding(0.dp)
            .background(Color(0xFF273A60))) {
            Text(
                text = "Dashboard",
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.CenterHorizontally),
                fontSize = 24.sp,
                color = Color.White
            )

            // Second zone for the map
             AndroidView (
                modifier = Modifier
                    .padding(0.dp)
                    .fillMaxWidth()
                    .height(245.dp)
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
                            onClick = { socketManager.moveLeft() },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF273A60), contentColor = Color.White),
                            shape = CircleShape,
                            border = BorderStroke(1.dp, Color.White),
                            modifier = Modifier.fillMaxSize(),
                            enabled = isStarted and !isAuto
                        ) {
                            Text("Q", style = TextStyle(fontSize = 20.sp), textAlign = TextAlign.Center)
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(80.dp)
                    ) {
                        Box(modifier = Modifier.size(50.dp)) {
                            Button(
                                onClick = {socketManager.moveForward()},
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF273A60), contentColor = Color.White),
                                shape = CircleShape,
                                border = BorderStroke(1.dp, Color.White),
                                modifier = Modifier.fillMaxSize(),
                                enabled = isStarted and !isAuto
                            ) {
                                Text("W", style = TextStyle(fontSize = 20.sp), textAlign = TextAlign.Center)
                            }
                        }

                        Box(modifier = Modifier.size(50.dp)) {
                            Button(
                                onClick = { socketManager.moveBackward()},
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF273A60), contentColor = Color.White),
                                shape = CircleShape,
                                border = BorderStroke(1.dp, Color.White),
                                modifier = Modifier.fillMaxSize(),
                                enabled = isStarted and !isAuto
                            ) {
                                Text("S", style = TextStyle(fontSize = 20.sp), textAlign = TextAlign.Center)
                            }
                        }
                    }
                    Box(modifier = Modifier.size(50.dp)) {
                        Button(
                            onClick = {socketManager.moveRight()},
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF273A60), contentColor = Color.White),
                            shape = CircleShape,
                            border = BorderStroke(1.dp, Color.White),
                            modifier = Modifier.fillMaxSize(),
                            enabled = isStarted and !isAuto
                        ) {
                            Text("D", style = TextStyle(fontSize = 20.sp), textAlign = TextAlign.Center)
                        }
                    }
                }
            }

            // Add buttons Start and Stop
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(
                    onClick = {
                        expanded = !expanded
                    },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF273A60), contentColor = Color.White),
                    border = BorderStroke(1.dp, Color.White),
                ) {
                    Text(text = "A", color = Color.White)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.width(IntrinsicSize.Min)
                ) {
                    DropdownMenuItem(onClick = { socketManager.driverModeAuto()
                        selectedOption = "Automatic Driving"
                        expanded = false
                    }) {
                        Text(text = "Automatic Driving")
                    }
                    DropdownMenuItem(onClick = {socketManager.driverModeMan()
                        selectedOption = "Manual Driving"
                        expanded = false
                    }) {
                        Text(text = "Manual Driving")
                    }
                }
                Button(
                    onClick = { socketManager.startMower()  },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF273A60), contentColor = Color.White),
                    border = BorderStroke(1.dp, Color.White),
                    enabled = !isStarted
                ) {
                    Text("Start")
                }

                Button(
                    onClick = { socketManager.stopMower() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF273A60), contentColor = Color.White),
                    border = BorderStroke(1.dp, Color.White),
                    enabled = isStarted
                ) {
                    Text("Stop")
                }
            }
            
            // Add the navbar
            NavBar(navController = navController)
        }
    }
    ShowAlertDialog(showDialog, dialogTitle, dialogMessage)
}