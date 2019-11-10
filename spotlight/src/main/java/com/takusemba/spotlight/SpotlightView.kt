package com.takusemba.spotlight

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.core.content.ContextCompat

/**
 * Spotlight View which holds a current [Target] and show it properly.
 */
internal class SpotlightView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

  var backgroundColor: Int? = null

  private val paint = Paint()
  private val spotPaint = Paint()
  private var animator: ValueAnimator? = null
  private var currentTarget: Target? = null

  // TODO improve this
  fun isAnimating(): Boolean {
    return animator != null && !animator!!.isRunning && animator!!.animatedValue as Float > 0
  }

  init {
    bringToFront()
    setWillNotDraw(false)
    setLayerType(View.LAYER_TYPE_HARDWARE, null)
    spotPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    paint.color = ContextCompat.getColor(context, backgroundColor!!)
    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
    if (animator != null && currentTarget != null) {
      currentTarget!!.shape
          .draw(canvas, currentTarget!!.anchor, animator!!.animatedValue as Float, spotPaint)
    }
  }

  fun startSpotlight(
      duration: Long,
      animation: TimeInterpolator,
      listener: Animator.AnimatorListener
  ) {
    val objectAnimator = ObjectAnimator.ofFloat(this, "alpha", 0f, 1f)
    objectAnimator.duration = duration
    objectAnimator.interpolator = animation
    objectAnimator.addListener(listener)
    objectAnimator.start()
  }

  fun finishSpotlight(
      duration: Long,
      animation: TimeInterpolator,
      listener: Animator.AnimatorListener
  ) {
    val objectAnimator = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f)
    objectAnimator.duration = duration
    objectAnimator.interpolator = animation
    objectAnimator.addListener(listener)
    objectAnimator.start()
  }

  fun turnUp(target: Target, listener: Animator.AnimatorListener) {
    currentTarget = target
    animator = ValueAnimator.ofFloat(0f, 1f).apply {
      addUpdateListener { this@SpotlightView.invalidate() }
      interpolator = target.interpolator
      duration = target.duration
      addListener(listener)
    }
    animator?.start()
  }

  fun turnDown(listener: Animator.AnimatorListener) {
    if (currentTarget == null) {
      return
    }
    animator = ValueAnimator.ofFloat(1f, 0f).apply {
      addUpdateListener { this@SpotlightView.invalidate() }
      addListener(listener)
      interpolator = currentTarget?.interpolator
      duration = currentTarget?.duration ?: 0L
    }
    animator?.start()
  }
}
