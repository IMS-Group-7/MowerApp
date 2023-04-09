package se.ju.mobile.mowerapp.views

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.ui.res.painterResource
import se.ju.mobile.mowerapp.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

import se.ju.mobile.mowerapp.ui.theme.MowerAppTheme

@Composable
fun LawnMowerConnectionView(onConnect: () -> Unit) {
    MowerAppTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "To use this app, you first need to\nconnect the robot.",
                style = MaterialTheme.typography.h5.copy(fontSize = 20.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(1.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            RoundConnectionButton(
                onClick = { onConnect() },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }
    }
}


@Composable
fun ConnectedLawnMower() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "You're Connected to Your Mower!",
            style = MaterialTheme.typography.h5.copy(fontSize = 20.sp),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(1.dp)
        )

    }
}

@Composable
fun RoundConnectionButton(
    onClick: () -> Unit,
    color: Color = MaterialTheme.colors.primary,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White
        )
    ) {
        Icon(
            painter = painterResource(id = R.drawable.bluetooth_connect),
            contentDescription = "Bluetooth Icon",
            modifier = Modifier.size(24.dp),
            tint = Color(0xFF273A60)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Connect with the robot", color = Color(0xFF273A60))
    }
}
