package com.valentinerutto.orbmotion.orbs

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.orbs.engine.FrameDispatcher

@Composable
fun ThinkingOrb(
    modifier: Modifier = Modifier,
    state: OrbState = OrbState.CONNECTING,
    size: Float = 64f,
    speed: Float = 1f,
    elapsedSeconds: Float,
    color: Color = Color.White
) {
    val frame = remember(state, size, speed, elapsedSeconds) {
        FrameDispatcher.frameForState(
            state = state,
            size = size,
            t = elapsedSeconds,
            speedMultiplier = speed
        )
    }

    Canvas(modifier = modifier) {
        frame.lines.forEach { line ->
            drawLine(
                color = color.copy(alpha = line.alpha),
                start = Offset(line.x1, line.y1),
                end = Offset(line.x2, line.y2),
                strokeWidth = line.w,
                cap = Stroke.DefaultCap
            )
        }

        frame.dots.forEach { dot ->
            drawCircle(
                color = color.copy(alpha = dot.alpha),
                radius = dot.r,
                center = Offset(dot.x, dot.y)
            )
        }
    }
}

@Composable
public fun AnimatedThinkingOrb(
    modifier: Modifier = Modifier,
    state: OrbState = OrbState.CONNECTING,
    size: Float = 64f,
    speed: Float = 1f,
    color: Color = Color.White
) {
    var elapsed by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(state, size, speed) {
        var lastFrameNanos = 0L
        while (true) {
            val frameNanos = withFrameNanos { it }
            if (lastFrameNanos != 0L) {
                val dt = (frameNanos - lastFrameNanos) / 1_000_000_000f
                elapsed += dt * speed
            }
            lastFrameNanos = frameNanos
        }
    }

    ThinkingOrb(
        modifier = modifier,
        state = state,
        size = size,
        speed = speed,
        elapsedSeconds = elapsed,
        color = color
    )
}
