package com.valentinerutto.orbmotion.orbs.engine.engine

import com.valentinerutto.orbmotion.orbs.Dot
import com.valentinerutto.orbmotion.orbs.Line
import com.valentinerutto.orbmotion.orbs.ModeOpts
import com.valentinerutto.orbmotion.orbs.OrbFrame
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.sin
import kotlin.math.sqrt

internal object FrameGenerators {

    fun orbits(size: Float, t: Float, o: ModeOpts): OrbFrame {
        val cx = size / 2f
        val cy = size / 2f
        val r = (size / 2f) * 0.82f * o.spread
        val proj = Projectors.makeProj(t * 0.66f, 0.42f, cx, cy, r)
        val rs = FrameMath.radiusScale(size, o.rsPow)

        val dots = mutableListOf<Dot>()
        val orbitN = o.orbitN

        for (i in 0 until orbitN) {
            val a = (i.toFloat() / orbitN.toFloat()) * 2f * PI.toFloat()
            val x = cos(a)
            val y = sin(a)
            val z = 0.45f * sin(a * 3f + t * 2f)
            val l = sqrt(x * x + y * y + z * z)
            val nx = x / l
            val ny = y / l
            val nz = z / l
            val (px, py, pz) = proj(nx, ny, nz)
            dots += Dot(px, py, pz, (o.partR + o.partRDepth * ((pz + 1f) / 2f)) * rs, 0.7f)
        }

        for (i in 0 until o.ghostN) {
            val a = (i.toFloat() / o.ghostN.toFloat()) * 2f * PI.toFloat()
            val x = cos(a)
            val y = sin(a)
            val z = sin(a * 2.7f + t * 0.8f) * 0.8f
            val l = sqrt(x * x + y * y + z * z)
            val nx = x / l
            val ny = y / l
            val nz = z / l
            val (px, py, pz) = proj(nx, ny, nz)
            dots += Dot(px, py, pz, o.ghostR * rs, 0.3f, 0.4f)
        }

        return FinalizeFrame.finalize(dots, emptyList(), o.rMin)
    }

    fun globe(size: Float, t: Float, o: ModeOpts): OrbFrame {
        val cx = size / 2f
        val cy = size / 2f
        val r = (size / 2f) * 0.8f * o.spread
        val proj = Projectors.makeProj(t * 0.7f, 0.8f, cx, cy, r)
        val rs = FrameMath.radiusScale(size, o.rsPow)

        val dots = mutableListOf<Dot>()

        for (latIndex in 0 until o.latRings) {
            val lat = -PI.toFloat() / 2f + (latIndex + 0.5f) * PI.toFloat() / o.latRings.toFloat()
            for (lonIndex in 0 until o.lonDensity) {
                val lon = (lonIndex.toFloat() / o.lonDensity.toFloat()) * 2f * PI.toFloat()
                val scan = sin(lon + t * 1.2f) * 0.15f
                val x = cos(lat + scan) * cos(lon)
                val y = sin(lat + scan)
                val z = cos(lat + scan) * sin(lon)
                val (px, py, pz) = proj(x, y, z)
                val depth = (pz + 1f) / 2f
                dots += Dot(px, py, pz, (o.rBase + o.rDepth * depth) * rs, 0.6f - 0.4f * depth)
            }
        }
        return FinalizeFrame.finalize(dots, emptyList(), o.rMin)
    }

    fun rubik(size: Float, t: Float, o: ModeOpts): OrbFrame {
        val cx = size / 2f
        val cy = size / 2f
        val r = (size / 2f) * 0.8f * o.spread
        val proj = Projectors.makeProj(t * 0.75f, 0.9f, cx, cy, r)
        val rs = FrameMath.radiusScale(size, o.rsPow)

        val dots = mutableListOf<Dot>()
        for (latIndex in 0 until o.latRings) {
            val lat = -PI.toFloat() / 2f + (latIndex + 0.5f) * PI.toFloat() / o.latRings.toFloat()
            for (lonIndex in 0 until o.lonDensity) {
                val lon = (lonIndex.toFloat() / o.lonDensity.toFloat()) * 2f * PI.toFloat()
                val wobble = sin(lon * 3f + t * 2f + latIndex) * 0.25f
                val x = cos(lat + wobble) * cos(lon)
                val y = sin(lat + wobble)
                val z = cos(lat + wobble) * sin(lon)
                val (px, py, pz) = proj(x, y, z)
                val depth = (pz + 1f) / 2f
                dots += Dot(px, py, pz, (o.rBase + o.rDepth * depth) * rs, 0.62f - 0.45f * depth)
            }
        }
        return FinalizeFrame.finalize(dots, emptyList(), o.rMin)
    }

    fun wave(size: Float, t: Float, o: ModeOpts): OrbFrame {
        val cx = size / 2f
        val cy = size / 2f
        val r = (size / 2f) * 0.78f * o.spread
        val proj = Projectors.makeProj(t * 1.1f, 0.75f, cx, cy, r)
        val rs = FrameMath.radiusScale(size, o.rsPow)

        val dots = mutableListOf<Dot>()
        for (ringIndex in 0 until o.latRings) {
            val ringAngle = (ringIndex.toFloat() / o.latRings.toFloat()) * 2f * PI.toFloat()
            for (lonIndex in 0 until o.lonDensity) {
                val lon = (lonIndex.toFloat() / o.lonDensity.toFloat()) * 2f * PI.toFloat()
                val wave = sin(lon * 3f + t * 2f + ringAngle) * 0.25f
                val x = cos(lon) * (0.7f + wave)
                val y = sin(lon) * (0.7f + wave)
                val z = sin(ringAngle * 2f + t) * 0.8f
                val (px, py, pz) = proj(x, y, z)
                val depth = (pz + 1f) / 2f
                dots += Dot(px, py, pz, (o.rBase + o.rDepth * depth) * rs, 0.6f - 0.35f * depth)
            }
        }
        return FinalizeFrame.finalize(dots, emptyList(), o.rMin)
    }

    fun web(size: Float, t: Float, o: ModeOpts): OrbFrame {
        val cx = size / 2f
        val cy = size / 2f
        val r = (size / 2f) * 0.8f * o.spread
        val proj = Projectors.makeProj(t * 0.12f, 0.32f, cx, cy, r)
        val rs = FrameMath.radiusScale(size, o.rsPow)

        val nodes = mutableListOf<Triple<Float, Float, Float>>()
        for (i in 0 until o.nodeN) {
            val d = FrameMath.fibDir(i, o.nodeN)
            val x = d.first + 0.3f * (FrameMath.vNoise(i * 0.31f + 9f, t * 0.24f) - 0.5f) * 2f
            val y = d.second + 0.3f * (FrameMath.vNoise(i * 0.53f + 27f, t * 0.21f) - 0.5f) * 2f
            val z = d.third + 0.3f * (FrameMath.vNoise(i * 0.77f + 55f, t * 0.27f) - 0.5f) * 2f
            val length = sqrt(x * x + y * y + z * z)
            nodes += Triple(x / length, y / length, z / length)
        }

        val lines = mutableListOf<Line>()
        val dots = mutableListOf<Dot>()

        for (i in 0 until o.nodeN) {
            for (j in i + 1 until o.nodeN) {
                val dx = nodes[i].first - nodes[j].first
                val dy = nodes[i].second - nodes[j].second
                val dz = nodes[i].third - nodes[j].third
                val dist = sqrt(dx * dx + dy * dy + dz * dz)
                if (dist >= o.thr) continue

                val (x1, y1, z1) = proj(nodes[i].first, nodes[i].second, nodes[i].third)
                val (x2, y2, z2) = proj(nodes[j].first, nodes[j].second, nodes[j].third)
                val depth = ((z1 + z2) / 2f + 1f) / 2f

                lines += Line(
                    x1 = x1,
                    y1 = y1,
                    x2 = x2,
                    y2 = y2,
                    white = 0.42f,
                    alpha = (1f - dist / o.thr) * (0.3f + 0.55f * depth),
                    w = max(0.6f, o.lineW * rs)
                )
            }
        }

        for (i in 0 until o.nodeN) {
            val (px, py, z) = proj(nodes[i].first, nodes[i].second, nodes[i].third)
            val depth = (z + 1f) / 2f
            val pulse = 1f + 0.25f * sin(t * 1.4f + i * 2.7f)
            dots += Dot(px, py, z, (o.nodeR + o.nodeRDepth * depth) * pulse * rs, 0.55f - 0.45f * depth)
        }

        for (s in 0 until o.signals) {
            val seg = floor(t * 0.55f + s * 7.31f).toInt()
            val a = floor(FrameMath.hashD(seg.toFloat(), s * 3.1f + 1.7f) * o.nodeN).toInt()
            val b = floor(FrameMath.hashD(seg.toFloat(), s * 5.7f + 4.2f) * o.nodeN).toInt()
            if (a == b) continue

            val f = FrameMath.frac(t * 0.55f + s * 7.31f)
            val x = FrameMath.lerp(nodes[a].first, nodes[b].first, f)
            val y = FrameMath.lerp(nodes[a].second, nodes[b].second, f)
            val z = FrameMath.lerp(nodes[a].third, nodes[b].third, f)
            val l = max(1e-6f, sqrt(x * x + y * y + z * z))
            val (px, py, zr) = proj(x / l, y / l, z / l)
            val depth = (zr + 1f) / 2f

            dots += Dot(
                x = px,
                y = py,
                z = zr,
                r = (o.nodeR * 1.5f + o.nodeRDepth * depth) * rs,
                white = 0.05f,
                alpha = 0.5f + 0.5f * depth
            )
        }

        return FinalizeFrame.finalize(dots, lines, o.rMin)
    }

    fun braid(size: Float, t: Float, o: ModeOpts): OrbFrame {
        val cx = size / 2f
        val cy = size / 2f
        val r = (size / 2f) * 0.8f * o.spread
        val proj = Projectors.makeProj(t * 0.42f, 0.65f, cx, cy, r)
        val rs = FrameMath.radiusScale(size, o.rsPow)

        val dots = mutableListOf<Dot>()
        for (strand in 0 until 3) {
            for (i in 0 until o.strandN) {
                val a = (i.toFloat() / o.strandN.toFloat()) * 2f * PI.toFloat()
                val lane = strand - 1f
                val x = cos(a * o.turns + t * 1.2f + strand) * (0.7f + lane * 0.18f)
                val y = sin(a * o.turns + t * 1.2f + strand) * (0.7f + lane * 0.18f)
                val z = sin(a * 2.2f + t + strand) * 0.8f
                val (px, py, pz) = proj(x, y, z)
                val depth = (pz + 1f) / 2f
                dots += Dot(px, py, pz, (o.rBase + o.rDepth * depth) * rs, 0.65f - 0.45f * depth)
            }
        }
        return FinalizeFrame.finalize(dots, emptyList(), o.rMin)
    }

    fun ribbon(size: Float, t: Float, o: ModeOpts): OrbFrame {
        val cx = size / 2f
        val cy = size / 2f
        val r = (size / 2f) * 0.8f * o.spread
        val proj = Projectors.makeProj(t * 0.35f, 0.5f, cx, cy, r)
        val rs = FrameMath.radiusScale(size, o.rsPow)

        val dots = mutableListOf<Dot>()
        for (lane in 0 until o.lanes) {
            val band = (lane.toFloat() / o.lanes.toFloat()) * 2f - 1f
            for (i in 0 until o.segs) {
                val a = (i.toFloat() / o.segs.toFloat()) * 2f * PI.toFloat()
                val wobble = sin(a * 3f + t * 2f + lane) * 0.42f
                val x = cos(a + wobble) * (0.8f + band * 0.22f)
                val y = sin(a) * (0.7f + band * 0.18f)
                val z = sin(a * 2f + t * 1.2f) * 0.8f
                val (px, py, pz) = proj(x, y, z)
                val depth = (pz + 1f) / 2f
                dots += Dot(px, py, pz, (o.rBase + o.rDepth * depth) * rs, 0.58f - 0.4f * depth)
            }
        }
        return FinalizeFrame.finalize(dots, emptyList(), o.rMin)
    }

    fun ring(size: Float, t: Float, o: ModeOpts): OrbFrame {
        val cx = size / 2f
        val cy = size / 2f
        val r = (size / 2f) * 0.85f * o.spread
        val proj = Projectors.makeProj(0f, 0f, cx, cy, r)
        val rs = FrameMath.radiusScale(size, o.rsPow)

        val dots = mutableListOf<Dot>()
        for (lane in 0 until o.lanes) {
            val band = (lane.toFloat() / o.lanes.toFloat()) * 2f - 1f
            for (i in 0 until o.segs) {
                val a = (i.toFloat() / o.segs.toFloat()) * 2f * PI.toFloat()
                val wobble = sin(a * 3f + t * 2f + lane) * 0.5f
                val x = cos(a + wobble) * (0.8f + band * 0.2f)
                val y = sin(a + wobble) * (0.8f + band * 0.2f)
                val z = sin(a * 2f + t * 1.2f) * 0.4f
                val (px, py, pz) = proj(x, y, z)
                val depth = (pz + 1f) / 2f
                dots += Dot(px, py, pz, (o.rBase + o.rDepth * depth) * rs, 0.64f - 0.4f * depth)
            }
        }
        return FinalizeFrame.finalize(dots, emptyList(), o.rMin)
    }

    fun morph(size: Float, t: Float, o: ModeOpts): OrbFrame {
        val cx = size / 2f
        val cy = size / 2f
        val r = (size / 2f) * 0.7f * o.spread
        val rs = FrameMath.radiusScale(size, o.rsPow)

        val dots = mutableListOf<Dot>()
        val steps = max(16, (o.iconD * 60f).toInt())
        val sides = 3 + ((sin(t * 1.3f) + 1f) * 0.5f * 2f).toInt()

        for (i in 0 until steps) {
            val a = (i.toFloat() / steps.toFloat()) * 2f * PI.toFloat()
            val shapeA = a * sides
            val radius = 0.72f + 0.18f * sin(t * 2f + a * 4f)
            val x = cos(shapeA) * radius
            val y = sin(shapeA) * radius
            val z = 0.3f * sin(t * 2f + a * 2f)

            val px = cx + x * r
            val py = cy + y * r
            val pz = z
            val depth = (pz + 1f) / 2f
            dots += Dot(px, py, pz, max(0.2f, o.rDot * 100f * rs), 0.7f - 0.5f * depth)
        }
        return FinalizeFrame.finalize(dots, emptyList(), o.rMin)
    }
}
