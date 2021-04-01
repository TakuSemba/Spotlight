package com.takusemba.spotlight.shape

import android.animation.TimeInterpolator
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.view.View

class DynamicShape(val view: View, val padding: Padding = Padding()) : Shape {
  override val duration: Long
    get() = TODO("Not yet implemented")
  override val interpolator: TimeInterpolator
    get() = TODO("Not yet implemented")

  override fun draw(canvas: Canvas, point: PointF, value: Float, paint: Paint) {
    TODO("Not yet implemented")
  }

  override fun contains(point: PointF, value: Float): Boolean {
    TODO("Not yet implemented")
  }

  override fun getMeasurements(): ShapeDimensions {
    TODO("Not yet implemented")
  }

  class Padding(
      val left: Int = DEFAULT_PADDING, val top: Int = DEFAULT_PADDING,
      val right: Int = DEFAULT_PADDING, val bottom: Int = DEFAULT_PADDING,
      val radius: Int = DEFAULT_CORNER_RADIUS
  ) {
    constructor(horizontal: Int, vertical: Int, radius: Int) : this(horizontal, vertical,
        horizontal,
        vertical, radius)

    constructor(horizontal: Int, vertical: Int) : this(horizontal, vertical, horizontal,
        vertical)
  }

  companion object {
    val DEFAULT_PADDING = 10
    val DEFAULT_CORNER_RADIUS = 15
  }
}
