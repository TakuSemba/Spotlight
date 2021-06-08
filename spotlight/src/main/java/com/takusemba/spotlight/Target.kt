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
    val anchor: PointF,
    val shape: Shape,
    val effect: Effect,
    val overlay: View?,
    val listener: OnTargetListener?,
    val overlayAlignment: OverlayAlignment,
    val verticalMargin: Float
) {

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
        private var overlayAlignment: OverlayAlignment = OverlayAlignment.DEFAULT
        private var verticalMargin: Float = 0f

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

        fun setAnchors(view1: View, view2: View,xOffset:Float,yOffset:Float): Builder = apply  {
            val (x1,y1) = getXAndY(view1)
            val (x2,y2) = getXAndY(view2)

            setAnchor((x1+x2) / 2 -xOffset, (y1 + y2)/2 - yOffset)
        }

        private fun getXAndY(view:View): Pair<Float, Float> {
            val location1 = IntArray(2)
            view.getLocationInWindow(location1)
            val x = location1[0] + view.width / 2f
            val y = location1[1] + view.height / 2f
            return Pair(x,y)
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
        fun setOverlay(
            overlay: View
        ): Builder = apply {
          this.overlay = overlay
        }

      /**
       * Sets [OverlayAlignment] to notify the state of [OverlayAlignment].
       */
        fun setOverlayAlignment(overlayAlignment: OverlayAlignment): Builder = apply {
          this.overlayAlignment = overlayAlignment
        }

        /**
         * Sets vertical Margin between shape and overlay
         */
        fun setVerticalMargin(verticalMargin: Float): Builder = apply {
            this.verticalMargin = verticalMargin
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
            listener = listener,
            overlayAlignment = overlayAlignment,
            verticalMargin = verticalMargin
        )

        companion object {

            private val DEFAULT_ANCHOR = PointF(0f, 0f)

            private val DEFAULT_SHAPE = Circle(100f)

            private val DEFAULT_EFFECT = EmptyEffect()
        }
    }
}
