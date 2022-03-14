package com.takusemba.spotlight.shape

import android.animation.TimeInterpolator
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.view.animation.DecelerateInterpolator
import java.util.concurrent.TimeUnit

/**
 * [Shape] of Circle with customizable radius.
 */
class Circle @JvmOverloads constructor(
    private val radius: Float,
    override val duration: Long = DEFAULT_DURATION,
    override val interpolator: TimeInterpolator = DEFAULT_INTERPOLATOR
) : Shape {

  override fun draw(canvas: Canvas, point: PointF, value: Float, paint: Paint) {
    canvas.drawCircle(point.x, point.y, value * radius, paint)
  }

  override fun contains(anchor: PointF, point: PointF): Boolean {
    val xNorm = point.x - anchor.x
    val yNorm = point.y - anchor.y
    return (xNorm * xNorm + yNorm * yNorm) <= radius * radius
  }

  companion object {

    val DEFAULT_DURATION = TimeUnit.MILLISECONDS.toMillis(500)

    val DEFAULT_INTERPOLATOR = DecelerateInterpolator(2f)
  }
}
