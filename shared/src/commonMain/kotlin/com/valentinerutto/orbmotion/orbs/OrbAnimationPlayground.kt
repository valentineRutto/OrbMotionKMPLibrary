package com.valentinerutto.orbmotion.orbs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

/**
 * Simplified playground that targets the local `ThinkingOrb` API used in `App.kt`.
 * Keeps the implementation small and Material3-based so it can be dropped into `App.kt`.
 */
@Composable
public fun OrbAnimationPlaygroundScreen(modifier: Modifier = Modifier) {
    var elapsed by remember { mutableFloatStateOf(0f) }
    var selectedState by remember { mutableStateOf(OrbState.CONNECTING) }
    var orbSize by remember { mutableFloatStateOf(180f) }
    var speed by remember { mutableFloatStateOf(1f) }
    var orbColor by remember { mutableStateOf(Color.White) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(16)
            elapsed += 0.016f
        }
    }

    Column(modifier = modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // State selector
        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OrbState.values().forEach { st ->
                Button(onClick = { selectedState = st }, modifier = Modifier) {
                    Text(st.name)
                }
            }
        }

        // Size
        Text("Size: ${orbSize.toInt()}")
        Slider(value = orbSize, onValueChange = { orbSize = it }, valueRange = 64f..360f)

        // Speed
        Text("Speed: ${String.format("%.2f", speed)}x")
        Slider(value = speed, onValueChange = { speed = it }, valueRange = 0.1f..4f)

        // Color swatches
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            val colors = listOf(Color.White, Color.Cyan, Color.Magenta, Color.Yellow, Color.Green, Color.Red, Color.Blue)
            colors.forEach { c ->

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(c)
                        .clickable { orbColor = c }
                        .then(if (orbColor == c) Modifier else Modifier)
                )

            }
        }

        Divider()

        // Preview area
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

            Box(modifier = Modifier.weight(1f).height(280.dp), contentAlignment = Alignment.Center) {
                ThinkingOrb(
                    modifier = Modifier.size(orbSize.dp),
                    state = selectedState,
                    size = orbSize,
                    speed = speed,
                    elapsedSeconds = elapsed,
                    color = orbColor,
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}