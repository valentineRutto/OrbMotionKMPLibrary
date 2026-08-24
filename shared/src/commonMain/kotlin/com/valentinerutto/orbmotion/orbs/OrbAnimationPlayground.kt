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
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
    var darkTheme by remember { mutableStateOf(true) }

    val snippet = remember(selectedState, orbSize, speed, orbColor, elapsed) {
        buildOrbCodeSnippet(
            state = selectedState,
            size = orbSize,
            speed = speed,
            elapsedSeconds = elapsed,
            color = orbColor,
        )
    }

    val clipboard = LocalClipboardManager.current

       val background = if (darkTheme) Color(0xFF0B1020) else Color(0xFFF2F2F8)

    LaunchedEffect(Unit) {
        while (true) {
            delay(16)
            elapsed += 0.016f
        }
    }

   Surface(color = background, modifier = Modifier.fillMaxSize()) {

    Column(modifier = modifier.fillMaxSize().padding(25.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // State selector
        Text(
                       "Selected state",
                        fontWeight = FontWeight.Bold,
                        color = if (darkTheme) Color.White else Color.Black
                    )

        Row(modifier = Modifier.fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {

            OrbState.values().forEach { st ->
                Button(onClick = { selectedState = st }, modifier = Modifier) {
                    Text(st.name, color = if (darkTheme) Color.White else Color.Black)
                }
            }
        }

        // Size
        Text("Size: ${orbSize.toInt()}",color = if (darkTheme) Color.White else Color.Black)
        Slider(value = orbSize, onValueChange = { orbSize = it }, valueRange = 64f..360f)

        // Speed
        Text("Speed: ${String.format("%.2f", speed)}x" , color = if (darkTheme) Color.White else Color.Black)
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
            Box(modifier = Modifier.height(280.dp), contentAlignment = Alignment.Center) {
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

        Surface(
            color = Color(0xFF1E1E1E),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF262626))
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Kotlin",
                        color = Color(0xFFB0B0B0),
                        style = MaterialTheme.typography.labelLarge
                    )

                    TextButton(onClick = { clipboard.setText(AnnotatedString(snippet)) }) {
                        Text(
                            text = "Copy",
                            color = Color(0xFF7CC3FF)
                        )
                    }
                }

                SelectionContainer {
                    Text(
                        text = snippet,
                        color = Color(0xFFEAEAEA),
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    )
                }
            }
        }
    }
}
}

private fun buildOrbCodeSnippet(
    state: OrbState,
    size: Float,
    speed: Float,
    elapsedSeconds: Float,
    color: Color,
): String {
    val hex = color.toArgb().toUInt().toString(16).uppercase().padStart(8, '0')
    return buildString {
        appendLine("ThinkingOrb(")
        appendLine("    modifier = Modifier.size(${size.toInt()}.dp),")
        appendLine("    state = OrbState.${state.name},")
        appendLine("    size = ${formatFloat(size)},")
        appendLine("    speed = ${formatFloat(speed)},")
        appendLine("    elapsedSeconds = ${formatFloat(elapsedSeconds)},")
        append("    color = Color(0x${hex}),")
        appendLine()
        append(")")
    }
}

private fun formatFloat(value: Float): String = String.format("%.2f", value) + "f"