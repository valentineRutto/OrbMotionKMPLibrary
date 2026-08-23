package com.valentinerutto.orbmotion.orbs.engine.engine

import com.valentinerutto.orbmotion.orbs.Dot
import com.valentinerutto.orbmotion.orbs.Line
import com.valentinerutto.orbmotion.orbs.OrbFrame
import kotlin.math.max

internal object FinalizeFrame {
    fun finalize(dots: List<Dot>, lines: List<Line>, rMin: Float): OrbFrame {
        val visibleDots = dots
            .filter { it.alpha >= 0.02f }
            .map { it.copy(r = max(rMin, it.r)) }
            .sortedBy { it.z }

        val visibleLines = lines.filter { it.alpha >= 0.02f }

        return OrbFrame(
            dots = visibleDots,
            lines = visibleLines
        )
    }
}
