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
class RoundedRectangle @JvmOverloads constructor(
    private val height: Float,
    private val width: Float,
    private val radius: Float,
    override val duration: Long = DEFAULT_DURATION,
    override val interpolator: TimeInterpolator = DEFAULT_INTERPOLATOR
) : Shape {

  private lateinit var rect: RectF

  override fun draw(canvas: Canvas, point: PointF, value: Float, paint: Paint) {
    rect = constructRectF(value, point)
    canvas.drawRoundRect(rect, radius, radius, paint)
  }

  private fun constructRectF(
      value: Float, point: PointF
  ): RectF {
    val halfWidth = width / 2 * value
    val halfHeight = height / 2 * value
    val left = point.x - halfWidth
    val top = point.y - halfHeight
    val right = point.x + halfWidth
    val bottom = point.y + halfHeight
    return RectF(left, top, right, bottom)
  }

  override fun contains(point: PointF, value: Float): Boolean {
    return rect.contains(point.x, point.y)
  }

  override fun getMeasurements(): ShapeDimensions {
    return ShapeDimensions(rect.height() / 2, rect.width() / 2)
  }

  companion object {

    val DEFAULT_DURATION = TimeUnit.MILLISECONDS.toMillis(500)

    val DEFAULT_INTERPOLATOR = DecelerateInterpolator(2f)
  }
}

