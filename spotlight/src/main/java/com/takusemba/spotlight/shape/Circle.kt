package com.takusemba.spotlight.shape

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF

/**
 * [Shape] of Circle with customizable radius.
 */
class Circle(private val radius: Float) : Shape {

  override fun draw(canvas: Canvas, point: PointF, value: Float, paint: Paint) {
    canvas.drawCircle(point.x, point.y, value * radius, paint)
  }
}
