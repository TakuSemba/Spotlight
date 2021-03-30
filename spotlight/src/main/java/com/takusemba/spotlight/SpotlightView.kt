package com.takusemba.spotlight

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.animation.ValueAnimator.INFINITE
import android.animation.ValueAnimator.ofFloat
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import com.takusemba.spotlight.shape.CaptionOrientation

/**
 * [SpotlightView] starts/finishes [Spotlight], and starts/finishes a current [Target].
 */
internal class SpotlightView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    @ColorInt backgroundColor: Int,
) : FrameLayout(context, attrs, defStyleAttr) {

  private val backgroundPaint by lazy {
    Paint().apply { color = backgroundColor }
  }

  private val shapePaint by lazy {
    Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }
  }

  private val effectPaint by lazy { Paint() }

  private val invalidator = AnimatorUpdateListener { invalidate() }

  private var shapeAnimator: ValueAnimator? = null
  private var effectAnimator: ValueAnimator? = null
  private var target: Target? = null

  init {
    setWillNotDraw(false)
    setLayerType(View.LAYER_TYPE_HARDWARE, null)

    /**
     * Makes it possible to pass clicks through [SpotlightView] to underlying views.
     */
    setOnTouchListener(object : OnTouchListener {
      override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return if (target?.clickable == true) {
          if (isInsideTarget(event)) {
            v?.performClick()
            false
          } else {
            true
          }
        } else true
      }

      private fun isInsideTarget(event: MotionEvent?): Boolean {
        event?.let {
          val pointF = PointF(it.x, it.y)
          target?.let {
            return it.shape.contains(pointF)
          }
        }
        return false
      }
    })
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), backgroundPaint)
    val currentTarget = target
    val currentShapeAnimator = shapeAnimator
    val currentEffectAnimator = effectAnimator
    if (currentTarget != null && currentEffectAnimator != null && currentShapeAnimator != null && !currentShapeAnimator.isRunning) {
      currentTarget.effect.draw(
          canvas = canvas,
          point = currentTarget.anchor,
          value = currentEffectAnimator.animatedValue as Float,
          paint = effectPaint
      )
    }
    if (currentTarget != null && currentShapeAnimator != null) {
      currentTarget.shape.draw(
          canvas = canvas,
          point = currentTarget.anchor,
          value = currentShapeAnimator.animatedValue as Float,
          paint = shapePaint
      )
    }
  }

  /**
   * Starts [Spotlight].
   */
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

  /**
   * Finishes [Spotlight].
   */
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

  /**
   * Starts the provided [Target].
   */
  fun startTarget(target: Target) {
    removeAllViews()
    addView(target.overlay, MATCH_PARENT, MATCH_PARENT)

    this.target = target.apply {
      // adjust anchor in case where custom container is set.
      val location = IntArray(2)
      getLocationInWindow(location)
      val offset = PointF(location[0].toFloat(), location[1].toFloat())
      anchor.offset(-offset.x, -offset.y)
    }

    this.shapeAnimator?.removeAllListeners()
    this.shapeAnimator?.removeAllUpdateListeners()
    this.shapeAnimator?.cancel()
    this.shapeAnimator = ofFloat(0f, 1f).apply {
      duration = target.shape.duration
      interpolator = target.shape.interpolator
      addUpdateListener(invalidator)
      addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
          showCaptions(target)

          removeAllListeners()
          removeAllUpdateListeners()
        }

        override fun onAnimationCancel(animation: Animator) {
          removeAllListeners()
          removeAllUpdateListeners()
        }
      })
    }
    this.effectAnimator?.removeAllListeners()
    this.effectAnimator?.removeAllUpdateListeners()
    this.effectAnimator?.cancel()
    this.effectAnimator = ofFloat(0f, 1f).apply {
      startDelay = target.shape.duration
      duration = target.effect.duration
      interpolator = target.effect.interpolator
      repeatMode = target.effect.repeatMode
      repeatCount = INFINITE
      addUpdateListener(invalidator)
      addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
          removeAllListeners()
          removeAllUpdateListeners()
        }

        override fun onAnimationCancel(animation: Animator) {
          removeAllListeners()
          removeAllUpdateListeners()
        }
      })
    }
    shapeAnimator?.start()
    effectAnimator?.start()
  }

  private fun showCaptions(target: Target) {
    target.captions?.forEach { caption ->
      caption.view.apply {
        val lp = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams = lp

        val availableWidth = this@SpotlightView.width - caption.totalParentMargin()
        measure(
            MeasureSpec.makeMeasureSpec(availableWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        )

        lp.apply {
          width = availableWidth

          if (caption.orientation == CaptionOrientation.HORIZONTAL) {
            if (caption.margins.parentBefore == null) {
              if (caption.margins.parentAfter == null) {
                leftMargin = 0
                rightMargin = 0
                gravity = Gravity.CENTER_HORIZONTAL
              } else {
                leftMargin = (this@SpotlightView.width - caption.margins.parentAfter - caption.view.measuredWidth).coerceAtLeast(
                    0)
                rightMargin = caption.margins.parentAfter
              }
            } else {
              if (caption.margins.parentAfter == null) {
                leftMargin = caption.margins.parentBefore
                rightMargin = (this@SpotlightView.width - caption.margins.parentBefore - caption.view.measuredWidth).coerceAtMost(
                    this@SpotlightView.width)
              } else {
                leftMargin = caption.margins.parentBefore
                rightMargin = caption.margins.parentAfter
              }
            }

            val margin = 0 +
                target.shape.getMeasurements().halfHeight +
                caption.view.measuredHeight * (1 - caption.type.shiftCoefficient) / 2 +
                caption.margins.target

            topMargin = (target.anchor.y + margin * caption.type.shiftCoefficient).toInt()
          }
        }

        layoutParams = lp

        /**
         * Disables color blending.
         */
        setLayerType(LAYER_TYPE_SOFTWARE, null)

        addView(this)
      }
    }
  }

  /**
   * Finishes the current [Target].
   */
  fun finishTarget(listener: Animator.AnimatorListener) {
    val currentTarget = target ?: return

    hideCaptions(currentTarget)

    val currentAnimatedValue = shapeAnimator?.animatedValue ?: return
    shapeAnimator?.removeAllListeners()
    shapeAnimator?.removeAllUpdateListeners()
    shapeAnimator?.cancel()
    shapeAnimator = ofFloat(currentAnimatedValue as Float, 0f).apply {
      duration = currentTarget.shape.duration
      interpolator = currentTarget.shape.interpolator
      addUpdateListener(invalidator)
      addListener(listener)
      addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
          removeAllListeners()
          removeAllUpdateListeners()
        }

        override fun onAnimationCancel(animation: Animator) {
          removeAllListeners()
          removeAllUpdateListeners()
        }
      })
    }
    effectAnimator?.removeAllListeners()
    effectAnimator?.removeAllUpdateListeners()
    effectAnimator?.cancel()
    effectAnimator = null
    shapeAnimator?.start()
  }

  private fun hideCaptions(currentTarget: Target) {
    currentTarget.captions?.forEach {
      removeView(it.view)
    }
  }

  fun cleanup() {
    effectAnimator?.removeAllListeners()
    effectAnimator?.removeAllUpdateListeners()
    effectAnimator?.cancel()
    effectAnimator = null
    shapeAnimator?.removeAllListeners()
    shapeAnimator?.removeAllUpdateListeners()
    shapeAnimator?.cancel()
    shapeAnimator = null
    removeAllViews()
  }
}
