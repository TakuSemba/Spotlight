package com.takusemba.spotlight.shape

import android.animation.TimeInterpolator
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.view.animation.DecelerateInterpolator
import java.util.concurrent.TimeUnit
import kotlin.math.abs
import kotlin.math.pow

/**
 * [Shape] of RoundedRectangle with customizable height, width, and radius.
 */
class RoundedRectangle @JvmOverloads constructor(
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

  /**
   * Ellipsis function is used to check if point is in rounded rectangle.
   * Ellipsis doesn't guarantee ideal precision.
   * Check https://en.wikipedia.org/wiki/Squircle
   *
   * Calculated values:
   * - r = [0; widthHalf], where 0 - rectangle, widthHalf - "smooth" ellipse
   * - n = [2; inf], where - "smooth" ellipse, inf - rectangle
   */
  override fun contains(anchor: PointF, point: PointF): Boolean {
    val xNorm = point.x - anchor.x
    val yNorm = point.y - anchor.y
    val widthHalf = width / 2
    val heightHalf = height / 2
    val r = radius.coerceIn(minimumValue = 0f, maximumValue = widthHalf)
    val n = width / r
    return abs((xNorm / widthHalf)).pow(n) + abs((yNorm / heightHalf)).pow(n) <= 1
  }

  companion object {

    val DEFAULT_DURATION = TimeUnit.MILLISECONDS.toMillis(500)

    val DEFAULT_INTERPOLATOR = DecelerateInterpolator(2f)
  }
}

