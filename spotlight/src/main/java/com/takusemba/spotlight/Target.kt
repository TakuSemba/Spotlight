package com.takusemba.spotlight

import android.animation.TimeInterpolator
import android.graphics.PointF
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.takusemba.spotlight.shape.Circle
import com.takusemba.spotlight.shape.Shape
import java.util.concurrent.TimeUnit

class Target(
    val anchor: PointF,
    val shape: Shape,
    val duration: Long,
    val interpolator: TimeInterpolator,
    val overlay: View?,
    val listener: OnTargetListener?
) {

  class Builder {

    private var anchor: PointF = DEFAULT_ANCHOR
    private var shape: Shape = DEFAULT_SHAPE
    private var duration: Long = DEFAULT_DURATION
    private var interpolator: TimeInterpolator = DEFAULT_INTERPOLATOR
    private var overlay: View? = null
    private var listener: OnTargetListener? = null

    fun setAnchor(view: View): Builder = apply {
      val location = IntArray(2)
      view.getLocationInWindow(location)
      val x = location[0] + view.width / 2f
      val y = location[1] + view.height / 2f
      setAnchor(x, y)
    }

    fun setAnchor(x: Float, y: Float): Builder = apply {
      setAnchor(PointF(x, y))
    }

    fun setAnchor(anchor: PointF): Builder = apply {
      this.anchor = anchor
    }

    fun setShape(shape: Shape): Builder = apply {
      this.shape = shape
    }

    fun setDuration(duration: Long): Builder = apply {
      this.duration = duration
    }

    fun setInterpolator(interpolator: TimeInterpolator): Builder = apply {
      this.interpolator = interpolator
    }

    fun setOverlay(overlay: View): Builder = apply {
      this.overlay = overlay
    }

    fun setOnTargetListener(listener: OnTargetListener): Builder = apply {
      this.listener = listener
    }

    fun build() = Target(anchor, shape, duration, interpolator, overlay, listener)

    companion object {

      private val DEFAULT_ANCHOR = PointF(0f, 0f)

      private val DEFAULT_SHAPE = Circle(100f)

      private val DEFAULT_DURATION = TimeUnit.MILLISECONDS.toMillis(250)

      private val DEFAULT_INTERPOLATOR = DecelerateInterpolator(2f)
    }
  }
}