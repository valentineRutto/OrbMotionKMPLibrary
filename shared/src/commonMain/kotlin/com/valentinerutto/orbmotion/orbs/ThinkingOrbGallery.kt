package com.valentinerutto.orbmotion.orbs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp

@Composable
public fun ThinkingOrbGallery(
    modifier: Modifier = Modifier,
    size: OrbSize = OrbSize.Small,
    theme: OrbTheme = OrbTheme.Auto,
    speed: Float = 1f,
    dotColorOverride: Color? = null,
    backgroundOverride: Color? = null,
) {
    val baseBackground = backgroundOverride ?: when (theme) {
        OrbTheme.Auto, OrbTheme.Dark -> Color(0xFF0B1020)
        OrbTheme.Light -> Color(0xFFF2F2F8)
    }
    val orbColor = dotColorOverride ?: Color.White

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 96.dp),
        modifier = modifier.background(baseBackground),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(OrbState.entries) { state ->
            val elapsed = remember(state.name) { mutableFloatStateOf(0f) }
            Box(
                modifier = Modifier.padding(8.dp),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    ThinkingOrb(
                        modifier = Modifier.size(size.dpValue),
                        state = state,
                        size = size.floatValue,
                        speed = speed,
                        elapsedSeconds = elapsed.floatValue,
                        color = orbColor,
                    )
                    Text(
                        text = state.name,
                        color = if (theme == OrbTheme.Light) Color.Black else Color.White,
                        modifier = Modifier.padding(top = 8.dp),
                    )
                }
            }
        }
    }
}

@Composable
public fun OrbGallery(
    modifier: Modifier = Modifier,
    size: OrbSize = OrbSize.Small,
    theme: OrbTheme = OrbTheme.Auto,
    speed: Float = 1f,
    dotColorOverride: Color? = null,
    backgroundOverride: Color? = null,
) = ThinkingOrbGallery(
    modifier = modifier,
    size = size,
    theme = theme,
    speed = speed,
    dotColorOverride = dotColorOverride,
    backgroundOverride = backgroundOverride,
)
