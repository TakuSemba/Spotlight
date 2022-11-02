package com.takusemba.spotlight

import android.graphics.PointF
import android.view.View
import com.takusemba.spotlight.effet.Effect
import com.takusemba.spotlight.effet.EmptyEffect
import com.takusemba.spotlight.shape.Circle
import com.takusemba.spotlight.shape.Shape

/**
 * Target represents the spot that Spotlight will cast.
 */
class Target(
    val anchor: View,
    val shape: Shape,
    val effect: Effect,
    val overlay: View?,
    val listener: OnTargetListener?
) {

  val anchorPosition: PointF
    get() {
      val location = IntArray(2).also { anchor.getLocationInWindow(it) }
      return PointF(location[0] + anchor.width / 2f, location[1] + anchor.height / 2f)
    }

  /**
   * [Builder] to build a [Target].
   * All parameters should be set in this [Builder].
   */
  class Builder {
    private var shape: Shape = DEFAULT_SHAPE
    private var effect: Effect = DEFAULT_EFFECT
    private var overlay: View? = null
    private var listener: OnTargetListener? = null

    /**
     * Sets [shape] of the spot of [Target].
     */
    fun setShape(shape: Shape): Builder = apply {
      this.shape = shape
    }

    /**
     * Sets [effect] of the spot of [Target].
     */
    fun setEffect(effect: Effect): Builder = apply {
      this.effect = effect
    }

    /**
     * Sets [overlay] to be laid out to describe [Target].
     */
    fun setOverlay(overlay: View): Builder = apply {
      this.overlay = overlay
    }

    /**
     * Sets [OnTargetListener] to notify the state of [Target].
     */
    fun setOnTargetListener(listener: OnTargetListener): Builder = apply {
      this.listener = listener
    }

    fun build(anchor: View) = Target(
        anchor = anchor,
        shape = shape,
        effect = effect,
        overlay = overlay,
        listener = listener
    )

    companion object {
      private val DEFAULT_SHAPE = Circle(100f)

      private val DEFAULT_EFFECT = EmptyEffect()
    }
  }
}
