package com.valentinerutto.orbmotion.orbs


data class ModeOpts(
    val spread: Float = 1f,
    val rsPow: Float = 0.6f,
    val rMin: Float = 0.3f,

    val orbitN: Int = 12,
    val ghostN: Int = 40,
    val ghostR: Float = 0.9f,
    val ghostA: Float = 0.5f,
    val particles: Int = 3,
    val partR: Float = 1.2f,
    val partRDepth: Float = 1.6f,

    val latRings: Int = 17,
    val lonDensity: Int = 44,
    val rBase: Float = 0.6f,
    val rDepth: Float = 1.7f,
    val rBoost: Float = 1f,
    val inkFar: Float = 0.62f,
    val inkSpan: Float = 0.54f,

    val nodeN: Int = 30,
    val thr: Float = 0.72f,
    val nodeR: Float = 1.4f,
    val nodeRDepth: Float = 1.8f,
    val lineW: Float = 0.8f,
    val signals: Int = 5,

    val strandN: Int = 52,
    val turns: Float = 3f,
    val lanes: Int = 5,
    val segs: Int = 88,
    val bandMul: Float = 1f,
    val wobMul: Float = 1f,
    val spin: Float = 0f,
    val faceOn: Int = 0,

    val rDot: Float = 0.021f,
    val iconD: Float = 1f
)