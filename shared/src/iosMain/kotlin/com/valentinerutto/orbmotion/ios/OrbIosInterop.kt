package com.valentinerutto.orbmotion.ios

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import com.valentinerutto.orbmotion.orbs.AnimatedThinkingOrb
import com.valentinerutto.orbmotion.orbs.OrbSize
import com.valentinerutto.orbmotion.orbs.OrbState
import com.valentinerutto.orbmotion.orbs.OrbTheme
import com.valentinerutto.orbmotion.orbs.ThinkingOrbGallery
import platform.UIKit.UIViewController

public fun makeOrbLoadingIndicatorViewController(
    stateOrdinal: Int = 0,
    sizeDp: Float = 64f,
    speed: Float = 1f,
    colorArgb: Long = 0xFFFFFFFF,
): UIViewController = ComposeUIViewController {
    val state = OrbState.entries.getOrNull(stateOrdinal) ?: OrbState.CONNECTING
    val orbSize = when (sizeDp.toInt()) {
        0 -> OrbSize.Small
        2 -> OrbSize.Large
        else -> OrbSize.Custom(sizeDp.dp)
    }

    AnimatedThinkingOrb(
        modifier = Modifier.fillMaxSize(),
        state = state,
        size = orbSize.floatValue,
        speed = speed,
        color = Color(colorArgb),
    )
}

public fun makeThinkingOrbGalleryViewController(
    speed: Float = 1f,
    sizeDp: Float = 48f,
    themeOrdinal: Int = 0,
    dotColorArgb: Long = 0xFFFFFFFF,
    bgColorArgb: Long = 0x00000000,
): UIViewController = ComposeUIViewController {
    val orbSize = when (sizeDp.toInt()) {
        0 -> OrbSize.Small
        2 -> OrbSize.Large
        else -> OrbSize.Custom(sizeDp.dp)
    }
    val theme = OrbTheme.entries.getOrNull(themeOrdinal.coerceIn(0, OrbTheme.entries.size - 1))
        ?: OrbTheme.Auto

    ThinkingOrbGallery(
        modifier = Modifier.fillMaxSize(),
        size = orbSize,
        theme = theme,
        speed = speed,
        dotColorOverride = Color(dotColorArgb),
        backgroundOverride = Color(bgColorArgb),
    )
}
