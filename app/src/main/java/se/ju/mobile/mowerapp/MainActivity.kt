package se.ju.mobile.mowerapp

import androidx.constraintlayout.compose.ConstraintLayout
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import se.ju.mobile.mowerapp.ui.theme.MowerAppTheme
import se.ju.mobile.mowerapp.views.ConnectedLawnMower
import se.ju.mobile.mowerapp.views.LawnMowerConnectionView

import se.ju.mobile.mowerapp.views.MovingRobotArrow

import se.ju.mobile.mowerapp.views.RoundConnectionButton
import androidx.compose.ui.platform.LocalLifecycleOwner


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

                    MovingRobotArrow()

                    LawnMowerContent(onConnect = {

                        // onConnect callback
                    })
                }
            }
        }
    }
}
