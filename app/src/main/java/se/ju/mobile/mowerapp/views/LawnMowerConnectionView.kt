package se.ju.mobile.mowerapp.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import se.ju.mobile.mowerapp.ui.theme.MowerAppTheme

@Composable
fun LawnMowerConnectionView() {
    MowerAppTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Born to code...(NOT)")
        }
    }
}

