package com.takusemba.spotlight.shape

import android.animation.TimeInterpolator
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.view.animation.DecelerateInterpolator
import java.util.concurrent.TimeUnit

/**
 * [Shape] of RoundedRectangle with customizable height, width, and radius.
 */
class RoundedRectangle(
    private val height: Float,
    private val width: Float,
    private val radius: Float,
    override val duration: Long = DEFAULT_DURATION,
    override val interpolator: TimeInterpolator = DEFAULT_INTERPOLATOR
) : Shape {

  override fun draw(canvas: Canvas, point: PointF, value: Float, paint: Paint) {
    val halfWidth = width / 2 * value
    val halfHeight = height / 2 * value
    val left = point.x - halfWidth
    val top = point.y - halfHeight
    val right = point.x + halfWidth
    val bottom = point.y + halfHeight
    val rect = RectF(left, top, right, bottom)
    canvas.drawRoundRect(rect, radius, radius, paint)
  }

  override fun contains(point: PointF, x: Float, y: Float): Boolean {
    val halfWidth = width / 2
    val halfHeight = height / 2
    val left = point.x - halfWidth
    val top = point.y - halfHeight
    val right = point.x + halfWidth
    val bottom = point.y + halfHeight
    return x > left && x < right && y > top && y < bottom
  }

  companion object {

    val DEFAULT_DURATION = TimeUnit.MILLISECONDS.toMillis(500)

    val DEFAULT_INTERPOLATOR = DecelerateInterpolator(2f)
  }
}

