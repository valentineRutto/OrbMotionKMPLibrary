package com.valentinerutto.orbmotion

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.valentinerutto.orbmotion.orbs.OrbAnimationPlaygroundScreen
import com.valentinerutto.orbmotion.orbs.OrbState
import com.valentinerutto.orbmotion.orbs.ThinkingOrb
import kotlinx.coroutines.delay

@Composable
@Preview
fun App() {
    MaterialTheme {

        var elapsed by remember { mutableFloatStateOf(0f) }
        var selectedState by remember { mutableStateOf(OrbState.CONNECTING) }
        var orbSize by remember { mutableStateOf(180f) }
        var speed by remember { mutableStateOf(1f) }
        var orbColor by remember { mutableStateOf(Color.White) }
        var darkTheme by remember { mutableStateOf(true) }

        LaunchedEffect(Unit) {
            while (true) {
                delay(16)
                elapsed += 0.016f
            }
        }

        OrbAnimationPlaygroundScreen(modifier = Modifier.fillMaxSize())
//
//        val background = if (darkTheme) Color(0xFF0B1020) else Color(0xFFF2F2F8)
//
//        Surface(color = background, modifier = Modifier.fillMaxSize()) {
//
//        Column(modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)) {
//
//            // Controls
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                Column(modifier = Modifier.weight(1f)) {
//                    Text(
//                        "Selected state",
//                        fontWeight = FontWeight.Bold,
//                        color = if (darkTheme) Color.White else Color.Black
//                    )
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .horizontalScroll(rememberScrollState())
//                    ) {
//
//                        OrbState.values().forEach { st ->
//                            val selected = st == selectedState
//                            Button(
//                                onClick = { selectedState = st },
//                                modifier = Modifier.padding(end = 8.dp)
//                            ) {
//                                Text(st.name, fontSize = 12.sp)
//                            }
//                        }
//
//
//                    }
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    Text(
//                        "Size: ${orbSize.toInt()}",
//                        fontWeight = FontWeight.Bold,
//                        color = if (darkTheme) Color.White else Color.Black
//                    )
//                    Slider(
//                        value = orbSize,
//                        onValueChange = { orbSize = it },
//                        valueRange = 64f..360f
//                    )
//
//                    Text(
//                        "Speed: ${String.format("%.2f", speed)}x",
//                        fontWeight = FontWeight.Bold,
//                        color = if (darkTheme) Color.White else Color.Black
//                    )
//                    Slider(
//                        value = speed,
//                        onValueChange = { speed = it },
//                        valueRange = 0.1f..4f
//                    )
//
//
//                    // Color and theme
//                    Column(modifier = Modifier.padding(start = 12.dp)) {
//                        Text(
//                            "Color",
//                            fontWeight = FontWeight.Bold,
//                            color = if (darkTheme) Color.White else Color.Black
//                        )
//                        val colors = listOf(
//                            Color.White,
//                            Color.Cyan,
//                            Color.Magenta,
//                            Color.Yellow,
//                            Color.Green,
//                            Color.Red,
//                            Color.Blue
//                        )
//                        Row {
//                            colors.forEach { c ->
//                                Box(
//                                    modifier = Modifier
//                                        .size(36.dp)
//                                        .padding(end = 8.dp)
//                                        .clip(MaterialTheme.shapes.small)
//                                        .background(c)
//                                        .clickable { orbColor = c }
//                                        .then(if (orbColor == c) Modifier.shadow(4.dp) else Modifier))
//                            }
//                        }
//
//                        Spacer(modifier = Modifier.height(12.dp))
//
//                        Button(onClick = { darkTheme = !darkTheme }) {
//                            Text(if (darkTheme) "Switch to Light" else "Switch to Dark")
//                        }
//                    }
//                }
//            }
//
//            Divider(modifier = Modifier.padding(vertical = 12.dp))
//
//            // Preview area
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
//                    ThinkingOrb(
//                        modifier = Modifier.size(orbSize.dp),
//                        state = selectedState,
//                        size = orbSize,
//                        speed = speed,
//                        elapsedSeconds = elapsed,
//                        color = orbColor
//                    )
//
//                }
//
//                Spacer(modifier = Modifier.width(12.dp))
//
//                // All states list with small previews
////                LazyColumn(modifier = Modifier.weight(1f)) {
////                    items(OrbState.entries) { st ->
////                        Row(modifier = Modifier
////                            .fillMaxWidth()
////                            .padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
////                            ThinkingOrb(modifier = Modifier.size(64.dp), state = st, size = 64f, elapsedSeconds = elapsed, color = orbColor)
////                            Spacer(modifier = Modifier.width(12.dp))
////                            Text(st.name, color = if (darkTheme) Color.White else Color.Black)
////                        }
////                        Divider()
////                    }
////                }
//            }
//        }
//    }
}}