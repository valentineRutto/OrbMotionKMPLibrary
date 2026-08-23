package com.valentinerutto.orbmotion.orbs.engine.engine

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

internal object FrameMath {
    fun lerp(a: Float, b: Float, f: Float): Float = a + (b - a) * f

    fun frac(x: Float): Float = x - floor(x)

    fun hashD(a: Float, b: Float): Float {
        val h = sin(a * 12.9898f + b * 78.233f) * 43758.5453f
        return h - floor(h)
    }

    fun fibDir(i: Int, n: Int): Triple<Float, Float, Float> {
        val golden = PI.toFloat() * (3f - sqrt(5f))
        val y = 1f - (2f * (i + 0.5f)) / n
        val rad = sqrt(1f - y * y)
        val a = i * golden
        return Triple(rad * cos(a), y, rad * sin(a))
    }

    fun radiusScale(size: Float, power: Float): Float = (size / 300f).pow(power)

    fun vNoise(x: Float, y: Float): Float {
        val xi = floor(x).toInt()
        val yi = floor(y).toInt()

        var fx = x - xi
        var fy = y - yi

        fx = fx * fx * (3f - 2f * fx)
        fy = fy * fy * (3f - 2f * fy)

        val a = hashD(xi.toFloat(), yi.toFloat())
        val b = hashD((xi + 1).toFloat(), yi.toFloat())
        val c = hashD(xi.toFloat(), (yi + 1).toFloat())
        val d = hashD((xi + 1).toFloat(), (yi + 1).toFloat())

        return a + (b - a) * fx + (c - a) * fy + (a - b - c + d) * fx * fy
    }
}
