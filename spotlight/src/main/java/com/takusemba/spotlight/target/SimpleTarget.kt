package com.takusemba.spotlight.target

import android.animation.TimeInterpolator
import android.app.Activity
import android.graphics.PointF
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.takusemba.spotlight.R
import com.takusemba.spotlight.shape.Shape

/**
 * SimpleTarget will set a simple overlay.
 * If you set your customized overlay, consider using [CustomTarget] instead.
 */
class SimpleTarget private constructor(
    override val shape: Shape,
    override val point: PointF,
    override val overlay: View,
    override val duration: Long,
    override val animation: TimeInterpolator
) : Target {

  class Builder(context: Activity) : AbstractTargetBuilder<Builder, SimpleTarget>(context) {

    private var title: CharSequence? = null
    private var description: CharSequence? = null
    private var overlayPoint: PointF? = null

    override fun self(): Builder {
      return this
    }

    fun setTitle(title: CharSequence): Builder = apply {
      this.title = title
    }

    fun setDescription(description: CharSequence): Builder = apply {
      this.description = description
    }

    fun setOverlayPoint(overlayPoint: PointF): Builder = apply {
      this.overlayPoint = overlayPoint
    }

    fun setOverlayPoint(x: Float, y: Float): Builder = apply {
      this.overlayPoint = PointF(x, y)
    }

    public override fun build(): SimpleTarget {
      val root = FrameLayout(context)
      val overlay = LayoutInflater.from(context).inflate(R.layout.layout_spotlight, root)
      val titleView = overlay.findViewById<TextView>(R.id.title)
      val descriptionView = overlay.findViewById<TextView>(R.id.description)
      val layout = overlay.findViewById<ViewGroup>(R.id.container)
      title?.let { title ->
        titleView.text = title
      }
      description?.let { description ->
        descriptionView.text = description
      }
      overlayPoint?.let { overlayPoint ->
        layout.x = overlayPoint.x
        layout.y = overlayPoint.y
      }
      return SimpleTarget(shape, point, overlay, duration, animation)
    }
  }
}