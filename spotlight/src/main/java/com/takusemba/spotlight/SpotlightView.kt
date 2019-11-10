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
    this.target = target
    animator = ValueAnimator.ofFloat(0f, 1f).apply {
      addUpdateListener { invalidate() }
      interpolator = target.interpolator
      duration = target.duration
      addListener(listener)
    }
    animator?.start()
  }

  fun turnDown(listener: Animator.AnimatorListener) {
    val currentTarget = target ?: return
    animator = ValueAnimator.ofFloat(1f, 0f).apply {
      addUpdateListener { invalidate() }
      addListener(listener)
      interpolator = currentTarget.interpolator
      duration = currentTarget.duration ?: 0L
    }
    animator?.start()
  }
}
