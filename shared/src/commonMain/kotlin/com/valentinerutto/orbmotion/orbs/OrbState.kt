package com.valentinerutto.orbmotion.orbs

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class OrbState {
    WORKING,
    SEARCHING,
    SOLVING,
    LISTENING,
    CONNECTING,
    WEAVING,
    COMPOSING,
    BREATHING,
    SHAPING
}

enum class OrbMode {
    ORBITS,
    GLOBE,
    RUBIK,
    WAVE,
    WEB,
    BRAID,
    RIBBON,
    RING,
    MORPH
}

enum class OrbTheme {
    Auto,
    Light,
    Dark,
}

sealed class OrbSize {
    data object Small : OrbSize()
    data object Large : OrbSize()
    data class Custom(val value: Dp) : OrbSize()

    val dpValue: Dp
        get() = when (this) {
            Small -> 48.dp
            Large -> 96.dp
            is Custom -> value
        }

    val floatValue: Float
        get() = dpValue.value
}

