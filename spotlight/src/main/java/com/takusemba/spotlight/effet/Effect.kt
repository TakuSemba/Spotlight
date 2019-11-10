package com.takusemba.spotlight.effet

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF

/**
 * Additional effect drawing in loop to Shape.
 */
interface Effect {

  /**
   * Draw the Effect.
   *
   * @param value the animated value from 0 to 1 and this value is looped until Target finishes.
   */
  fun draw(canvas: Canvas, point: PointF, value: Float, paint: Paint)
}