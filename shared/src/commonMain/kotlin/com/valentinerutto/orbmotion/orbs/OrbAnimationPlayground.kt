package com.valentinerutto.orbmotion.orbs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

/**
 * Simplified playground that targets the local `ThinkingOrb` API used in `App.kt`.
 * Keeps the implementation small and Material3-based so it can be dropped into `App.kt`.
 */
@Composable
public fun OrbAnimationPlaygroundScreen(modifier: Modifier = Modifier) {
    var elapsed by remember { mutableFloatStateOf(0f) }
    var selectedState by remember { mutableStateOf(OrbState.SEARCHING) }
    var orbSize by remember { mutableFloatStateOf(360f) }
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
    val textColor = if (darkTheme) Color.White else Color.Black
    val panelColor = if (darkTheme) Color(0xFF1D2333) else Color(0xFFE9ECF5)
    val mutedText = if (darkTheme) Color(0xFFB8C1D9) else Color(0xFF44516A)
    val trackColor = if (darkTheme) Color(0xFF4B5368) else Color(0xFFCFD6EA)
    val activeTrackColor = if (darkTheme) Color(0xFFB6A4FF) else Color(0xFF7B63E6)
    val thumbColor = Color.White

    LaunchedEffect(Unit) {
        while (true) {
            delay(16)
            elapsed += 0.016f
        }
    }

    Surface(color = background, modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Orb Playground",
                    color = textColor,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = "Select state",
                color = textColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OrbState.values().forEach { st ->
                    val selected = st == selectedState
                    StateChip(
                        label = st.name,
                        selected = selected,
                        onClick = { selectedState = st }
                    )
                }
            }

            Surface(
                color = panelColor,
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Size", color = textColor, fontSize = 20.sp)
                        Text("${orbSize.toInt()}", color = textColor, fontSize = 20.sp)
                    }
                    Slider(
                        value = orbSize,
                        onValueChange = { orbSize = it },
                        valueRange = 64f..360f,
                        colors = SliderDefaults.colors(
                            thumbColor = thumbColor,
                            activeTrackColor = activeTrackColor,
                            inactiveTrackColor = trackColor,
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Speed", color = textColor, fontSize = 20.sp)
                        Text("${String.format("%.2f", speed)}x", color = textColor, fontSize = 20.sp)
                    }
                    Slider(
                        value = speed,
                        onValueChange = { speed = it },
                        valueRange = 0.1f..4f,
                        colors = SliderDefaults.colors(
                            thumbColor = thumbColor,
                            activeTrackColor = activeTrackColor,
                            inactiveTrackColor = trackColor,
                        )
                    )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Color Palette", color = textColor, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = "#${orbColor.toArgb().toUInt().toString(16).uppercase().padStart(6, '0')}",
                    color = textColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val colors = listOf(
                    Color.White,
                    Color(0xFF3DB7F4),
                    Color(0xFFFF4C9A),
                    Color(0xFFFFD93D),
                    Color(0xFF2AE39F),
                    Color(0xFFFF4D4D)
                )

                colors.forEach { c ->
                    val selected = orbColor == c
                    Box(
                        modifier = Modifier
                            .size(if (selected) 42.dp else 38.dp)
                            .clip(CircleShape)
                            .background(c)
                            .clickable { orbColor = c }
                            .then(if (selected) Modifier.border(2.dp, Color.White, CircleShape) else Modifier)
                    )
                }
            }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .background(Color(0xFF111C2D), CircleShape)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                ThinkingOrb(
                    modifier = Modifier.size((orbSize.coerceAtMost(220f)).dp),
                    state = selectedState,
                    size = orbSize,
                    speed = speed,
                    elapsedSeconds = elapsed,
                    color = orbColor,
                )
            }

            Surface(
                color = Color(0xFF1E1E1E),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF262626))
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
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

@Composable
private fun StateChip(label: String, selected: Boolean, onClick: () -> Unit) {
    val container = if (selected) Color(0xFFB7A4FF) else Color(0xFF2A3347)
    val content = if (selected) Color(0xFF101A2E) else Color.White

    Button(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = container,
            contentColor = content
        ),
        modifier = Modifier.height(42.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
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