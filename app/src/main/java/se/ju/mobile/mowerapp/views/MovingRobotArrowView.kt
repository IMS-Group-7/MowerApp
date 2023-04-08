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
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset
import androidx.compose.material.ButtonDefaults

import se.ju.mobile.mowerapp.ui.theme.MowerAppTheme

@Composable
fun MovingRobotArrow() {
    var isStarted by remember { mutableStateOf(false) }

    MowerAppTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Dashboard",
                modifier = Modifier.padding(vertical = 16.dp)
                    .align(Alignment.CenterHorizontally),
                fontSize = 24.sp
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
                Canvas(
                    modifier = Modifier.matchParentSize(),
                    onDraw = {
                        val diamondRadius = size.minDimension / 4

                        val topCircleCenter = Offset(size.width / 2, size.height / 2 - diamondRadius)
                        val rightCircleCenter = Offset(size.width / 2 + diamondRadius, size.height / 2)
                        val bottomCircleCenter = Offset(size.width / 2, size.height / 2 + diamondRadius)
                        val leftCircleCenter = Offset(size.width / 2 - diamondRadius, size.height / 2)

                        val circleRadius = size.minDimension / 12

                        // Top Circle
                        drawCircle(
                            color = Color(0xFFC1B8FD),
                            radius = circleRadius,
                            center = topCircleCenter,
                        )

                        // Right Circle
                        drawCircle(
                            color = Color(0xFFC1B8FD),
                            radius = circleRadius,
                            center = rightCircleCenter,
                        )

                        // Bottom Circle
                        drawCircle(
                            color = Color(0xFFC1B8FD),
                            radius = circleRadius,
                            center = bottomCircleCenter,
                        )

                        // Left Circle
                        drawCircle(
                            color = Color(0xFFC1B8FD),
                            radius = circleRadius,
                            center = leftCircleCenter,
                        )
                    }
                )
            }

            // Add buttons Start et Stop
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(
                    onClick = { isStarted = true },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFC1B8FD)),
                    enabled = !isStarted
                ) {
                    Text("Start")
                }

                Button(
                    onClick = { isStarted = false },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFC1B8FD)),
                    enabled = isStarted
                ) {
                    Text("Stop")
                }
            }
        }
    }
}