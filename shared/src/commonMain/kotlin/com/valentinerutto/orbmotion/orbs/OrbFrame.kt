package com.valentinerutto.orbmotion.orbs
data class Dot(
    val x: Float,
    val y: Float,
    val z: Float,
    val r: Float,
    val white: Float,
    val alpha: Float = 1f
)

data class Line(
    val x1: Float,
    val y1: Float,
    val x2: Float,
    val y2: Float,
    val white: Float,
    val alpha: Float = 1f,
    val w: Float
)

data class OrbFrame(
    val dots: List<Dot>,
    val lines: List<Line>
)