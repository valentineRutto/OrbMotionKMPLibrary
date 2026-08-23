package com.valentinerutto.orbmotion.orbs


data class ResolvedPreset(
    val mode: OrbMode,
    val speed: Float,
    val opts: ModeOpts
)

object OrbPresets {
    private val stateToMode = mapOf(
        OrbState.WORKING to OrbMode.ORBITS,
        OrbState.SEARCHING to OrbMode.GLOBE,
        OrbState.SOLVING to OrbMode.RUBIK,
        OrbState.LISTENING to OrbMode.WAVE,
        OrbState.CONNECTING to OrbMode.WEB,
        OrbState.WEAVING to OrbMode.BRAID,
        OrbState.COMPOSING to OrbMode.RIBBON,
        OrbState.BREATHING to OrbMode.RING,
        OrbState.SHAPING to OrbMode.MORPH
    )

    fun resolvePreset(state: OrbState, size: Float): ResolvedPreset {
        val mode = stateToMode[state] ?: OrbMode.WEB
        val speed = when (mode) {
            OrbMode.ORBITS -> if (size >= 32f) 1.885f else 3.9f
            OrbMode.GLOBE -> if (size >= 32f) 2.015f else 2.665f
            OrbMode.RUBIK -> if (size >= 32f) 1.82f else 1.95f
            OrbMode.WAVE -> if (size >= 32f) 4.388f else 3.998f
            OrbMode.WEB -> if (size >= 32f) 3.315f else 6.63f
            OrbMode.BRAID -> if (size >= 32f) 1.625f else 2.75f
            OrbMode.RIBBON -> if (size >= 32f) 2.34f else 3.12f
            OrbMode.RING -> if (size >= 32f) 3.24f else 3.78f
            OrbMode.MORPH -> if (size >= 32f) 2.405f else 2.08f
        }

        val opts = when (mode) {
            OrbMode.WEB -> ModeOpts(
                nodeN = 30,
                thr = 0.72f,
                signals = 5,
                nodeR = 1.4f,
                nodeRDepth = 1.8f,
                lineW = 0.8f,
                rsPow = 0.6f
            )
            OrbMode.GLOBE -> ModeOpts(
                latRings = 17,
                lonDensity = 44,
                rBase = 0.6f,
                rDepth = 1.7f,
                rsPow = 0.6f
            )
            OrbMode.ORBITS -> ModeOpts(
                orbitN = 12,
                ghostN = 40,
                ghostR = 0.9f,
                ghostA = 0.5f,
                particles = 3,
                partR = 1.2f,
                partRDepth = 1.6f,
                rsPow = 0.6f
            )
            OrbMode.RUBIK -> ModeOpts(
                latRings = 15,
                lonDensity = 40,
                rBase = 0.6f,
                rDepth = 1.7f,
                rsPow = 0.6f
            )
            OrbMode.WAVE -> ModeOpts(
                latRings = 15,
                lonDensity = 40,
                rBase = 0.6f,
                rDepth = 1.7f,
                rsPow = 0.6f
            )
            OrbMode.BRAID -> ModeOpts(
                strandN = 52,
                turns = 3f,
                ghostN = 150,
                rBase = 1.2f,
                rDepth = 1.8f,
                rsPow = 0.6f
            )
            OrbMode.RIBBON -> ModeOpts(
                lanes = 5,
                segs = 88,
                ghostN = 150,
                rBase = 1.1f,
                rDepth = 1.7f,
                rsPow = 0.6f
            )
            OrbMode.RING -> ModeOpts(
                lanes = 5,
                segs = 88,
                ghostN = 0,
                faceOn = 1,
                rBase = 1.1f,
                rDepth = 1.7f,
                rsPow = 0.6f
            )
            OrbMode.MORPH -> ModeOpts(
                rDot = 0.021f,
                iconD = 1f,
                rMin = 0.25f
            )
        }

        return ResolvedPreset(mode, speed, opts)
    }
}
