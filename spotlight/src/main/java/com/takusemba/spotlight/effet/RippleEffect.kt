package com.takusemba.spotlight.effet

import android.animation.TimeInterpolator
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import androidx.annotation.ColorInt
import com.takusemba.spotlight.effet.Effect.Companion.DEFAULT_DURATION
import com.takusemba.spotlight.effet.Effect.Companion.DEFAULT_INTERPOLATOR

/**
 * Draws an ripple effects.
 */
class RippleEffect(
    private val offset: Float,
    private val radius: Float,
    @ColorInt private val color: Int,
    override val duration: Long = DEFAULT_DURATION,
    override val interpolator: TimeInterpolator = DEFAULT_INTERPOLATOR
) : Effect {

  init {
    require(offset < radius) { "holeRadius should be bigger than rippleRadius." }
  }

  override fun draw(canvas: Canvas, point: PointF, value: Float, paint: Paint) {
    paint.color = color
    val offset = (radius - offset) * value
    canvas.drawCircle(point.x, point.y, offset + offset, paint)
  }
}