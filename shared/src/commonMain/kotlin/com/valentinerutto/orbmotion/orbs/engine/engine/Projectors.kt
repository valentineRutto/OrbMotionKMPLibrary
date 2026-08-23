package com.valentinerutto.orbmotion.orbs.engine.engine

import kotlin.math.cos
import kotlin.math.sin

internal object Projectors {
    fun makeProj(
        yaw: Float,
        tilt: Float,
        cx: Float,
        cy: Float,
        scale: Float
    ): (Float, Float, Float) -> Triple<Float, Float, Float> {
        val st = sin(tilt)
        val ct = cos(tilt)
        val sy = sin(yaw)
        val cyw = cos(yaw)

        return { x, y, z ->
            val x1 = x * cyw + z * sy
            val z1 = -x * sy + z * cyw
            val y1 = y * ct - z1 * st
            val z2 = y * st + z1 * ct
            Triple(cx + x1 * scale, cy - y1 * scale, z2)
        }
    }
}
