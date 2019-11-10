package com.takusemba.spotlight.shape

import android.animation.TimeInterpolator
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.view.animation.DecelerateInterpolator
import com.takusemba.spotlight.Target
import java.util.concurrent.TimeUnit

/**
 * Shape of a [Target] that would be drawn by Spotlight View.
 * For any shape of target, this Shape class need to be implemented.
 */
interface Shape {

  /**
   * [duration] to draw Shape.
   */
  val duration: Long
    get() = DEFAULT_DURATION

  /**
   * [interpolator] to draw Shape.
   */
  val interpolator: TimeInterpolator
    get() = DEFAULT_INTERPOLATOR

  /**
   * Draws the Shape.
   *
   * @param value the animated value from 0 to 1.
   */
  fun draw(canvas: Canvas, point: PointF, value: Float, paint: Paint)

  companion object {

    private val DEFAULT_DURATION = TimeUnit.MILLISECONDS.toMillis(500)

    private val DEFAULT_INTERPOLATOR = DecelerateInterpolator(2f)
  }
}
