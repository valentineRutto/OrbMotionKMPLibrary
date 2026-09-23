package com.valentinerutto.orbmotion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.valentinerutto.orbmotion.orbs.OrbLoadingIndicator
import com.valentinerutto.orbmotion.orbs.OrbState
import kotlinx.coroutines.delay

@Composable
fun DemoOrbScreen() {
    var elapsed by remember { mutableFloatStateOf(0f) }
    var selectedState by remember { mutableStateOf(OrbState.CONNECTING) }
    var speed by remember { mutableFloatStateOf(1f) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(16)
            elapsed += 0.016f
        }
    }

    Surface(color = Color.Black, modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "OrbMotion Demo",
                color = Color.White,
                fontSize = 28.sp,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OrbState.entries.forEach { state ->
                    val selected = state == selectedState
                    Button(
                        onClick = { selectedState = state },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selected) Color.White else Color(0xFF2A3347),
                            contentColor = if (selected) Color.Black else Color.White
                        ),
                        modifier = Modifier.height(38.dp)
                    ) {
                        Text(state.name)
                    }
                }
            }

            Slider(
                value = speed,
                onValueChange = { speed = it },
                valueRange = 0.1f..4f,
                modifier = Modifier.fillMaxWidth(),
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                OrbLoadingIndicator(
                    modifier = Modifier.size(180.dp),
                    state = selectedState,
                    size = 180f,
                    speed = speed,
                    elapsedSeconds = elapsed,
                    color = Color.White
                )
            }
        }
    }
}
