package com.takusemba.spotlight

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.annotation.ColorRes
import com.takusemba.spotlight.target.Target
import java.util.concurrent.TimeUnit

/**
 * Spotlight that holds all the [Target]s and show and hide [Target] properly, and show
 * and hide [SpotlightView] properly.
 */
class Spotlight<T : Target> private constructor(
    private val context: Context,
    private val targets: ArrayList<T>,
    private val duration: Long,
    private val animation: TimeInterpolator,
    private val spotlightListener: OnSpotlightListener?,
    private val targetListener: OnTargetListener<T>?,
    @ColorRes private val overlayColor: Int,
    private val isClosedOnTouchedOutside: Boolean
) {

  private var spotlightView: SpotlightView? = null

  /**
   * Shows [SpotlightView]
   */
  fun start() {
    spotlightView()
  }

  /**
   * close the current [Target]
   */
  fun closeCurrentTarget() {
    finishTarget()
  }

  /**
   * close the [Spotlight]
   */
  fun closeSpotlight() {
    finishSpotlight()
  }

  /**
   * Creates the spotlight view and starts
   */
  private fun spotlightView() {
    val decorView = (context as Activity).window.decorView
    val spotlightView = SpotlightView(context)
    spotlightView.overlayColor = overlayColor
    spotlightView.setOnClickListener {
      if (spotlightView.isAnimating() && isClosedOnTouchedOutside) {
        finishTarget()
      }
    }
    this.spotlightView = spotlightView
    (decorView as ViewGroup).addView(spotlightView)
    startSpotlight()
  }

  /**
   * show Target
   */
  private fun startTarget() {
    if (targets.size > 0 && spotlightView != null) {
      val target = targets[0]
      val spotlightView = spotlightView
      spotlightView!!.removeAllViews()
      spotlightView.addView(target.overlay)
      spotlightView.turnUp(target, object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animation: Animator) {
          targetListener?.onStarted(target)
        }
      })
    }
  }

  /**
   * show Spotlight
   */
  private fun startSpotlight() {
    if (spotlightView == null) return
    spotlightView!!.startSpotlight(duration, animation, object : AnimatorListenerAdapter() {
      override fun onAnimationStart(animation: Animator) {
        spotlightListener?.onStarted()
      }

      override fun onAnimationEnd(animation: Animator) {
        startTarget()
      }
    })
  }

  /**
   * hide Target
   */
  private fun finishTarget() {
    if (targets.size > 0 && spotlightView != null) {
      spotlightView!!.turnDown(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
          if (targets.isNotEmpty()) {
            val target = targets.removeAt(0)
            targetListener?.onEnded(target)
            if (targets.size > 0) {
              startTarget()
            } else {
              finishSpotlight()
            }
          }
        }
      })
    }
  }

  /**
   * hide Spotlight
   */
  private fun finishSpotlight() {
    if (spotlightView == null) return
    spotlightView!!.finishSpotlight(duration, animation, object : AnimatorListenerAdapter() {
      override fun onAnimationEnd(animation: Animator) {
        val activity = context as Activity?
        if (activity != null) {
          val decorView = activity.window.decorView
          (decorView as ViewGroup).removeView(spotlightView)
          spotlightListener?.onEnded()
        }
      }
    })
  }

  class Builder<T : Target>(private val context: Context) {

    private val targets: ArrayList<T> = ArrayList()
    private var duration: Long = DEFAULT_DURATION
    private var animation: TimeInterpolator = DEFAULT_ANIMATION
    private var onSpotlightListener: OnSpotlightListener? = null
    private var onTargetListener: OnTargetListener<T>? = null
    @ColorRes private var overlayColor: Int = DEFAULT_OVERLAY_COLOR
    private var isClosedOnTouchedOutside: Boolean = true

    fun addTargets(vararg targets: T): Builder<T> = apply {
      for (target in targets) {
        if (!this.targets.contains(target)) {
          this.targets.add(target)
        }
      }
    }

    fun addTargets(targets: List<T>): Builder<T> = apply {
      for (target in targets) {
        if (!this.targets.contains(target)) {
          this.targets.add(target)
        }
      }
    }

    fun setDuration(duration: Long): Builder<T> = apply {
      this.duration = duration
    }

    fun setOverlayColor(@ColorRes overlayColor: Int): Builder<T> = apply {
      this.overlayColor = overlayColor
    }

    fun setAnimation(animation: TimeInterpolator): Builder<T> = apply {
      this.animation = animation
    }

    fun setOnTargetStateChangedListener(listener: OnTargetListener<T>): Builder<T> = apply {
      onTargetListener = listener
    }

    fun setOnSpotlightStateListener(listener: OnSpotlightListener): Builder<T> = apply {
      onSpotlightListener = listener
    }

    fun setClosedOnTouchedOutside(isClosedOnTouchedOutside: Boolean): Builder<T> = apply {
      this.isClosedOnTouchedOutside = isClosedOnTouchedOutside
    }

    fun build(): Spotlight<T> {
      return Spotlight(
          context = context,
          targets = targets,
          duration = duration,
          animation = animation,
          spotlightListener = onSpotlightListener,
          targetListener = onTargetListener,
          overlayColor = overlayColor,
          isClosedOnTouchedOutside = isClosedOnTouchedOutside
      )
    }
  }

  companion object {

    @ColorRes private val DEFAULT_OVERLAY_COLOR = R.color.background
    private val DEFAULT_DURATION = TimeUnit.SECONDS.toMillis(1)
    private val DEFAULT_ANIMATION = DecelerateInterpolator(2f)
  }
}
