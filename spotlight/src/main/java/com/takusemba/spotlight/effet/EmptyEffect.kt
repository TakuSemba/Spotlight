package com.takusemba.spotlight.effet

import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.view.animation.LinearInterpolator
import java.util.concurrent.TimeUnit

/**
 * [Effect] that does not do anything.
 */
class EmptyEffect(
    override val duration: Long = DEFAULT_DURATION,
    override val interpolator: TimeInterpolator = DEFAULT_INTERPOLATOR,
    override val repeatMode: Int = DEFAULT_REPEAT_MODE
) : Effect {

  override fun draw(canvas: Canvas, point: PointF, value: Float, paint: Paint) = Unit

  companion object {

    val DEFAULT_DURATION = TimeUnit.MILLISECONDS.toMillis(0)

    val DEFAULT_INTERPOLATOR = LinearInterpolator()

    const val DEFAULT_REPEAT_MODE = ObjectAnimator.REVERSE
  }
}