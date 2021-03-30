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
class Circle @JvmOverloads constructor(
    private val radius: Float,
    override val duration: Long = DEFAULT_DURATION,
    override val interpolator: TimeInterpolator = DEFAULT_INTERPOLATOR
) : Shape {
  private lateinit var circleCenter: PointF

  override fun draw(canvas: Canvas, point: PointF, value: Float, paint: Paint) {
    circleCenter = point
    canvas.drawCircle(point.x, point.y, value * radius, paint)
  }

  override fun contains(point: PointF, value: Float): Boolean {
    val fl = (circleCenter.x - point.x).toDouble()
    val fl1 = (circleCenter.y - point.y).toDouble()
    return hypot(fl, fl1) <= radius
  }

  override fun getMeasurements(): ShapeDimensions {
    return ShapeDimensions(radius, radius)
  }

  companion object {

    val DEFAULT_DURATION = TimeUnit.MILLISECONDS.toMillis(500)

    val DEFAULT_INTERPOLATOR = DecelerateInterpolator(2f)
  }
}
