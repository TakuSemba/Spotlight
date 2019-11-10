package com.takusemba.spotlight.effet

import android.animation.TimeInterpolator
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import com.takusemba.spotlight.effet.Effect.Companion.DEFAULT_DURATION
import com.takusemba.spotlight.effet.Effect.Companion.DEFAULT_INTERPOLATOR

/**
 * [Effect] that does not do anything.
 */
class EmptyEffect(
    override val duration: Long = DEFAULT_DURATION,
    override val interpolator: TimeInterpolator = DEFAULT_INTERPOLATOR
) : Effect {

  override fun draw(canvas: Canvas, point: PointF, value: Float, paint: Paint) = Unit
}