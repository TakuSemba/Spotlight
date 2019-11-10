package com.takusemba.spotlight.shape

import android.animation.TimeInterpolator
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import com.takusemba.spotlight.shape.Shape.Companion.DEFAULT_DURATION
import com.takusemba.spotlight.shape.Shape.Companion.DEFAULT_INTERPOLATOR

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
}
