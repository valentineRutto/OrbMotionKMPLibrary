package com.example.orbs.engine

import com.valentinerutto.orbmotion.orbs.OrbFrame
import com.valentinerutto.orbmotion.orbs.OrbMode
import com.valentinerutto.orbmotion.orbs.OrbPresets
import com.valentinerutto.orbmotion.orbs.OrbState
import com.valentinerutto.orbmotion.orbs.engine.engine.FrameGenerators

object FrameDispatcher {
    fun frameForState(
        state: OrbState,
        size: Float,
        t: Float,
        speedMultiplier: Float = 1f
    ): OrbFrame {

        val preset = OrbPresets.resolvePreset(state, size)
        val baseT = t * preset.speed * speedMultiplier

        return when (preset.mode) {
            OrbMode.ORBITS -> FrameGenerators.orbits(size, baseT, preset.opts)
            OrbMode.GLOBE -> FrameGenerators.globe(size, baseT, preset.opts)
            OrbMode.RUBIK -> FrameGenerators.rubik(size, baseT, preset.opts)
            OrbMode.WAVE -> FrameGenerators.wave(size, baseT, preset.opts)
            OrbMode.WEB -> FrameGenerators.web(size, baseT, preset.opts)
            OrbMode.BRAID -> FrameGenerators.braid(size, baseT, preset.opts)
            OrbMode.RIBBON -> FrameGenerators.ribbon(size, baseT, preset.opts)
            OrbMode.RING -> FrameGenerators.ring(size, baseT, preset.opts)
            OrbMode.MORPH -> FrameGenerators.morph(size, baseT, preset.opts)
        }
    }
}
