package com.takusemba.spotlight

import android.graphics.PointF
import android.view.View
import com.takusemba.spotlight.effet.Effect
import com.takusemba.spotlight.effet.EmptyEffect
import com.takusemba.spotlight.shape.Caption
import com.takusemba.spotlight.shape.Circle
import com.takusemba.spotlight.shape.DynamicShape
import com.takusemba.spotlight.shape.RoundedRectangle
import com.takusemba.spotlight.shape.Shape

/**
 * Target represents the spot that Spotlight will cast.
 */
class Target(
    var anchor: PointF,
    var shape: Shape,
    val effect: Effect,
    val overlay: View?,
    val captions: List<Caption>?,
    val listener: OnTargetListener?,
    val clickable: Boolean,
    var visibilityEnforcer: (() -> Unit)?,
    val visibilityChecker: (() -> Boolean)?,
    var anchorChecker: (() -> Boolean)?,
    var anchorRebuilder: (() -> Unit)?
) {
  /**
   * Resets [anchor] and [shape] for a [Target] that just became visible.
   */
  fun recalculateShape(view: View?, dynamicShape: DynamicShape) {
    view?.let {
      setAnchor(it)
      setShape(dynamicShape)
    }
  }

  /**
   * Sets a pointer to start a [Target].
   */
  private fun setAnchor(view: View) {
    val location = IntArray(2)
    view.getLocationInWindow(location)
    val x = location[0] + view.width / 2f
    val y = location[1] + view.height / 2f
    setAnchor(x, y)
  }

  /**
   * Sets an anchor point to start [Target].
   */
  private fun setAnchor(x: Float, y: Float) {
    setAnchor(PointF(x, y))
  }

  /**
   * Sets an anchor point to start [Target].
   */
  @JvmName("setAnchor1") private fun setAnchor(anchor: PointF) {
    this.anchor = anchor
  }

  /**
   * Sets [shape] of the spot of [Target].
   */
  @JvmName("setShape1") private fun setShape(shape: Shape) {
    if (shape is DynamicShape) {
      shape.view.let {
        val rect = RoundedRectangle(
            height = it.measuredHeight.toFloat() + shape.padding.top,
            width = it.measuredWidth.toFloat() + shape.padding.left,
            radius = shape.padding.radius.toFloat()
        )
        this.shape = rect
      }
    } else {
      this.shape = shape
    }
  }

  /**
   * Sets the [visibilityEnforcer] callback.
   */
  fun setActionToMakeVisible(action: (() -> Unit)?) = this.apply {
    visibilityEnforcer = action
  }

  /**
   * Sets [anchorChecker] of the [Target].
   */
  fun setAnchorChecker(checker: () -> Boolean) = this.apply {
    anchorChecker = checker
  }

  /**
   * Sets the [anchorRebuilder] callback.
   */
  fun setAnchorRebuilder(rebuilder: () -> Unit) = this.apply {
    anchorRebuilder = rebuilder
  }

  /**
   * Convenience methods.
   */
  fun isNotVisible() = visibilityChecker?.let { it() } == false
  fun isNotCentered() = anchorChecker?.let { it() } == false

  /**
   * [Builder] to build a [Target].
   * All parameters should be set in this [Builder].
   */
  class Builder {

    private var anchor: PointF = DEFAULT_ANCHOR
    private var shape: Shape = DEFAULT_SHAPE
    private var effect: Effect = DEFAULT_EFFECT
    private var overlay: View? = null
    private var captions: List<Caption>? = null
    private var listener: OnTargetListener? = null
    private var clickable: Boolean = true
    private var visibilityEnforcer: (() -> Unit)? = null
    private var visibilityChecker: (() -> Boolean)? = null
    private var anchorChecker: (() -> Boolean)? = null
    private var anchorRebuilder: (() -> Unit)? = null

    /**
     * Sets a pointer to start a [Target].
     */
    fun setAnchor(view: View): Builder = apply {
      val location = IntArray(2)
      view.getLocationInWindow(location)
      val x = location[0] + view.width / 2f
      val y = location[1] + view.height / 2f
      setAnchor(x, y)
    }

    /**
     * Sets an anchor point to start [Target].
     */
    fun setAnchor(x: Float, y: Float): Builder = apply {
      setAnchor(PointF(x, y))
    }

    /**
     * Sets an anchor point to start [Target].
     */
    fun setAnchor(anchor: PointF): Builder = apply {
      this.anchor = anchor
    }

    /**
     * Sets [shape] of the spot of [Target].
     */
    fun setShape(shape: Shape): Builder = apply {
      if (shape is DynamicShape) {
        shape.view.let {
          val rect = RoundedRectangle(
              height = it.measuredHeight.toFloat() + shape.padding.top,
              width = it.measuredWidth.toFloat() + shape.padding.left,
              radius = shape.padding.radius.toFloat()
          )
          this.shape = rect
        }
      } else {
        this.shape = shape
      }
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
     * Sets [captions] for the [Target].
     */
    fun setCaptions(captions: List<Caption>): Builder = apply {
      this.captions = captions
    }

    /**
     * Sets [OnTargetListener] to notify the state of [Target].
     */
    fun setOnTargetListener(listener: OnTargetListener): Builder = apply {
      this.listener = listener
    }

    /**
     * Defines if the [Target] is clickable.
     */
    fun setClickable(clickable: Boolean): Builder = apply {
      this.clickable = clickable
    }

    /**
     * Sets the [visibilityChecker] callback.
     */
    fun setVisibilityChecker(checker: (() -> Boolean)?): Builder = apply {
      this.visibilityChecker = checker
    }

    fun build() = Target(
        anchor = anchor,
        shape = shape,
        effect = effect,
        overlay = overlay,
        captions = captions,
        listener = listener,
        clickable = clickable,
        visibilityEnforcer = visibilityEnforcer,
        visibilityChecker = visibilityChecker,
        anchorChecker = anchorChecker,
        anchorRebuilder = anchorRebuilder,
    )

    companion object {

      val DEFAULT_ANCHOR = PointF(0f, 0f)

      val DEFAULT_SHAPE = Circle(100f)

      private val DEFAULT_EFFECT = EmptyEffect()
    }
  }
}