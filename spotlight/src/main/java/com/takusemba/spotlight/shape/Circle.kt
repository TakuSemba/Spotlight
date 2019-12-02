package com.takusemba.spotlight.shape

import android.animation.TimeInterpolator
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.view.animation.DecelerateInterpolator
import java.util.concurrent.TimeUnit
import kotlin.math.hypot

/**
 * [Shape] of Circle with customizable radius.
 */
class Circle(
    private val radius: Float,
    override val duration: Long = DEFAULT_DURATION,
    override val interpolator: TimeInterpolator = DEFAULT_INTERPOLATOR
) : Shape {

  override fun draw(canvas: Canvas, point: PointF, value: Float, paint: Paint) {
    canvas.drawCircle(point.x, point.y, value * radius, paint)
  }

  override fun contains(point: PointF, x: Float, y: Float): Boolean {
    val dist = hypot((point.x - x).toDouble(), (point.y - y).toDouble())
    return dist < radius
  }

  companion object {

    val DEFAULT_DURATION = TimeUnit.MILLISECONDS.toMillis(500)

    val DEFAULT_INTERPOLATOR = DecelerateInterpolator(2f)
  }
}
