package com.takusemba.spotlight.shape

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF

/**
 * Shape of a [Target] that would be drawn by Spotlight View.
 * For any shape of target, this Shape class need to be implemented.
 */
interface Shape {

  /**
   * Draws the Shape
   *
   * @param value the animated value from 0 to 1
   */
  fun draw(canvas: Canvas, point: PointF, value: Float, paint: Paint)
}
