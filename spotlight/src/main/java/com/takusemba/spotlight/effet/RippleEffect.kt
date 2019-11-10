package com.takusemba.spotlight.effet

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import androidx.annotation.ColorInt

/**
 * Draws an ripple effects.
 */
class RippleEffect(
    private val holeRadius: Float,
    private val rippleRadius: Float,
    @ColorInt private val color: Int
) : Effect {

  init {
    require(holeRadius < rippleRadius) { "holeRadius should be bigger than rippleRadius." }
  }

  override fun draw(canvas: Canvas, point: PointF, value: Float, paint: Paint) {
    paint.alpha = (value * 255f).toInt()
    paint.color = color
    val offset = (rippleRadius - holeRadius) * value
    canvas.drawCircle(point.x, point.y, holeRadius + offset, paint)
  }
}