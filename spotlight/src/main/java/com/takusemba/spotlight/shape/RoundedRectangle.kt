package com.takusemba.spotlight.shape

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF

/**
 * [Shape] of RoundedRectangle with customizable height, width, and radius.
 */
class RoundedRectangle(
    private val height: Float,
    private val width: Float,
    private val radius: Float
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
}

