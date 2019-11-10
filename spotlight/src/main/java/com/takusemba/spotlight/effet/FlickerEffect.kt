package com.takusemba.spotlight.effet

import android.animation.TimeInterpolator
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.view.animation.LinearInterpolator
import androidx.annotation.ColorInt

/**
 * Draws an flicker effects.
 */
class FlickerEffect(
    private val radius: Float,
    @ColorInt private val color: Int,
    override val duration: Long = 3000,
    override val interpolator: TimeInterpolator = LinearInterpolator()
) : Effect {

  override fun draw(canvas: Canvas, point: PointF, value: Float, paint: Paint) {
    paint.color = color
    paint.alpha = if (value < 0.5) {
      (value * 2 * 255).toInt()
    } else {
      255 - ((value - 0.5) * 2 * 255).toInt()
    }
    canvas.drawCircle(point.x, point.y, radius, paint)
  }
}