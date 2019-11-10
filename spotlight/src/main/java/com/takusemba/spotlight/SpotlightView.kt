package com.takusemba.spotlight

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat

/**
 * Spotlight View which holds a current [Target] and show it properly.
 */
internal class SpotlightView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    backgroundColor: Int = R.color.background
) : FrameLayout(context, attrs, defStyleAttr) {

  private val backgroundPaint by lazy {
    Paint().apply { ContextCompat.getColor(context, backgroundColor) }
  }

  private val targetPaint by lazy {
    Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }
  }

  private val invalidator = AnimatorUpdateListener { invalidate() }

  private var animator: ValueAnimator? = null
  private var target: Target? = null

  init {
    setWillNotDraw(false)
    setLayerType(View.LAYER_TYPE_HARDWARE, null)
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), backgroundPaint)
    val currentTarget = target
    val currentAnimator = animator
    if (currentTarget != null && currentAnimator != null) {
      currentTarget.shape.draw(
          canvas = canvas,
          point = currentTarget.anchor,
          value = currentAnimator.animatedValue as Float,
          paint = targetPaint
      )
    }
  }

  fun startSpotlight(
      duration: Long,
      interpolator: TimeInterpolator,
      listener: Animator.AnimatorListener
  ) {
    val objectAnimator = ObjectAnimator.ofFloat(this, "alpha", 0f, 1f).apply {
      setDuration(duration)
      setInterpolator(interpolator)
      addListener(listener)
    }
    objectAnimator.start()
  }

  fun finishSpotlight(
      duration: Long,
      interpolator: TimeInterpolator,
      listener: Animator.AnimatorListener
  ) {
    val objectAnimator = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f).apply {
      setDuration(duration)
      setInterpolator(interpolator)
      addListener(listener)
    }
    objectAnimator.start()
  }

  fun turnUp(target: Target, listener: Animator.AnimatorListener) {
    removeAllViews()
    addView(target.overlay)
    this.target = target
    this.animator = ValueAnimator.ofFloat(0f, 1f).apply {
      duration = target.duration
      interpolator = target.interpolator
      addUpdateListener(invalidator)
      addListener(listener)
    }
    animator?.start()
  }

  fun turnDown(listener: Animator.AnimatorListener) {
    if (animator == null) return
    val currentTarget = target ?: return
    val animator = ValueAnimator.ofFloat(1f, 0f).apply {
      duration = currentTarget.duration
      interpolator = currentTarget.interpolator
      addUpdateListener(invalidator)
      addListener(listener)
    }
    animator.start()
    this.animator = null
    this.target = null
  }
}
