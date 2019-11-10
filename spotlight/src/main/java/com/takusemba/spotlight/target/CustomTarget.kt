package com.takusemba.spotlight.target

import android.animation.TimeInterpolator
import android.app.Activity
import android.graphics.PointF
import android.view.LayoutInflater
import android.view.View

import androidx.annotation.LayoutRes

import com.takusemba.spotlight.OnTargetStateChangedListener
import com.takusemba.spotlight.shape.Shape

/**
 * CustomTarget can set your own custom view for a overlay. If you do not care about much about a
 * overlay, consider using [SimpleTarget] instead.
 */
class CustomTarget private constructor(
    override val shape: Shape,
    override val point: PointF,
    override val overlay: View,
    override val duration: Long,
    override val animation: TimeInterpolator,
    override val listener: OnTargetStateChangedListener<CustomTarget>?
) : Target<CustomTarget> {

  class Builder(context: Activity) : AbstractTargetBuilder<Builder, CustomTarget>(context) {

    private var overlay: View? = null

    override fun self(): Builder {
      return this
    }

    fun setOverlay(@LayoutRes layoutId: Int): Builder = apply {
      this.overlay = LayoutInflater.from(context).inflate(layoutId, null)
    }

    fun setOverlay(overlay: View): Builder = apply {
      this.overlay = overlay
    }

    public override fun build(): CustomTarget {
      val overlay = requireNotNull(overlay) { "Overlay have to be set." }
      return CustomTarget(shape, point, overlay, duration, animation, listener)
    }
  }
}
