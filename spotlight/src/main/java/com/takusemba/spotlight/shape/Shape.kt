package com.takusemba.spotlight.shape

import android.animation.TimeInterpolator
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import com.takusemba.spotlight.Target

/**
 * Shape of a [Target] that would be drawn by Spotlight View.
 * For any shape of target, this Shape class need to be implemented.
 */
interface Shape {

  /**
   * [duration] to draw Shape.
   */
  val duration: Long

  /**
   * [interpolator] to draw Shape.
   */
  val interpolator: TimeInterpolator

  /**
   * Draws the Shape.
   *
   * @param value the animated value from 0 to 1.
   */
  fun draw(canvas: Canvas, point: PointF, value: Float, paint: Paint)

  /**
   * Checks if point on edge or inside of the Shape.
   *
   * @param anchor center of Shape.
   * @param point point to check against contains.
   * @return true if contains, false - otherwise.
   */
  fun contains(anchor: PointF, point: PointF): Boolean
}
