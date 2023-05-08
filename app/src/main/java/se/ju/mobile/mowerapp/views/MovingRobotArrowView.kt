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
import se.ju.mobile.mowerapp.ui.theme.MowerAppTheme
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.foundation.layout.offset

@Composable
fun MovingRobotArrow() {
    var isStarted by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }

    MowerAppTheme {
        Column(modifier = Modifier.padding(16.dp) .background(Color(0xFF273A60))) {
            Text(
                text = "Dashboard",
                modifier = Modifier.padding(vertical = 16.dp)
                    .align(Alignment.CenterHorizontally),
                fontSize = 24.sp,
                color = Color.White
            )

            // Second zone for the map
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(Color.LightGray)
                    .padding(horizontal = 16.dp),
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
                            onClick = { },
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
                                onClick = { },
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
                                onClick = { },
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
                            onClick = { },
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

            // Add buttons Start et Stop
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
}