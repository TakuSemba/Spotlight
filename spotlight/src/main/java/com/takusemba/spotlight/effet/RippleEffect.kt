package com.takusemba.spotlight.effet

import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.view.animation.DecelerateInterpolator
import androidx.annotation.ColorInt
import java.util.concurrent.TimeUnit

/**
 * Draws an ripple effects.
 */
class RippleEffect @JvmOverloads constructor(
    private val offset: Float,
    private val radius: Float,
    @ColorInt private val color: Int,
    override val duration: Long = DEFAULT_DURATION,
    override val interpolator: TimeInterpolator = DEFAULT_INTERPOLATOR,
    override val repeatMode: Int = DEFAULT_REPEAT_MODE
) : Effect {

  init {
    require(offset < radius) { "holeRadius should be bigger than rippleRadius." }
  }

  override fun draw(canvas: Canvas, point: PointF, value: Float, paint: Paint) {
    val radius = offset + ((radius - offset) * value)
    val alpha = (255 - value * 255).toInt()
    paint.color = color
    paint.alpha = alpha
    canvas.drawCircle(point.x, point.y, radius, paint)
  }

  companion object {

    val DEFAULT_DURATION = TimeUnit.MILLISECONDS.toMillis(1000)

    val DEFAULT_INTERPOLATOR = DecelerateInterpolator(1f)

    const val DEFAULT_REPEAT_MODE = ObjectAnimator.REVERSE
  }
}