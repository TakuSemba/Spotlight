package com.takusemba.spotlight

import android.graphics.PointF
import android.view.View
import com.takusemba.spotlight.effet.Effect
import com.takusemba.spotlight.effet.EmptyEffect
import com.takusemba.spotlight.shape.Circle
import com.takusemba.spotlight.shape.RoundedRectangle
import com.takusemba.spotlight.shape.Shape
import kotlin.math.abs
import kotlin.math.pow

/**
 * Target represents the spot that Spotlight will cast.
 */
class Target(
    val anchor: PointF,
    val shape: Shape,
    val effect: Effect,
    val overlay: View?,
    val listener: OnTargetListener?
) {

  /**
   * Checks if point on edge or inside of the Shape.
   *
   * @param point point to check against contains
   * @return true if contains, false - otherwise
   */
  fun contains(point: PointF): Boolean {
    val xNorm = point.x - anchor.x
    val yNorm = point.y - anchor.y
    return when (shape) {
      is Circle -> {
        (xNorm * xNorm + yNorm * yNorm) <= shape.radius * shape.radius
      }
      is RoundedRectangle -> {
        // Ellipsis function is used to check if point is in rounded rectangle
        // Ellipsis doesn't guarantee ideal precision. Check https://en.wikipedia.org/wiki/Squircle
        val widthHalf = shape.width / 2
        val heightHalf = shape.height / 2
        // r = [0; widthHalf], where 0 - rectangle, widthHalf - "smooth" ellipse
        val r = shape.radius.coerceIn(minimumValue = 0f, maximumValue = widthHalf)
        // n = [2; inf], where - "smooth" ellipse, inf - rectangle
        val n = shape.width / r
        abs((xNorm / widthHalf)).pow(n) + abs((yNorm / heightHalf)).pow(n) <= 1
      }
      else -> throw IllegalStateException("Unknown shape: ${shape::class.qualifiedName}")
    }
  }

  /**
   * [Builder] to build a [Target].
   * All parameters should be set in this [Builder].
   */
  class Builder {

    private var anchor: PointF = DEFAULT_ANCHOR
    private var shape: Shape = DEFAULT_SHAPE
    private var effect: Effect = DEFAULT_EFFECT
    private var overlay: View? = null
    private var listener: OnTargetListener? = null

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

    fun build() = Target(
        anchor = anchor,
        shape = shape,
        effect = effect,
        overlay = overlay,
        listener = listener
    )

    companion object {

      private val DEFAULT_ANCHOR = PointF(0f, 0f)

      private val DEFAULT_SHAPE = Circle(100f)

      private val DEFAULT_EFFECT = EmptyEffect()
    }
  }
}