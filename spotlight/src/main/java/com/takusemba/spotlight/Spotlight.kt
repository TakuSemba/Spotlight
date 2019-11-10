package com.takusemba.spotlight

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.animation.DecelerateInterpolator
import androidx.annotation.ColorRes
import java.util.concurrent.TimeUnit

/**
 * Spotlight that holds all the [Target]s and show and hide [Target] properly, and show
 * and hide [SpotlightView] properly.
 */
class Spotlight private constructor(
    private val context: Context,
    private val targets: ArrayList<Target>, // TODO Add show previous target functionality.
    private val duration: Long,
    private val animation: TimeInterpolator,
    private val spotlightListener: OnSpotlightListener?,
    private val backgroundColor: Int
) {

  private var spotlightView = SpotlightView(context, null, 0, backgroundColor)

  init {
    // TODO give option to add on activity itself.
    val decorView = (context as Activity).window.decorView
    (decorView as ViewGroup).addView(spotlightView, MATCH_PARENT, MATCH_PARENT)
  }

  /**
   * Shows [SpotlightView]
   */
  fun start() {
    startSpotlight()
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
   * show Target
   */
  private fun startTarget() {
    if (targets.size > 0) {
      val target = targets[0]
      val spotlightView = spotlightView
      spotlightView.removeAllViews()
      spotlightView.addView(target.overlay)
      spotlightView.turnUp(target, object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animation: Animator) {
          target.listener?.onStarted(target)
        }
      })
    }
  }

  /**
   * show Spotlight
   */
  private fun startSpotlight() {
    spotlightView.startSpotlight(duration, animation, object : AnimatorListenerAdapter() {
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
    if (targets.size > 0) {
      spotlightView.turnDown(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
          if (targets.isNotEmpty()) {
            val target = targets.removeAt(0)
            target.listener?.onEnded(target)
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
    spotlightView.finishSpotlight(duration, animation, object : AnimatorListenerAdapter() {
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

  class Builder(private val context: Context) {

    private val targets: ArrayList<Target> = ArrayList()

    private var duration: Long = DEFAULT_DURATION
    private var animation: TimeInterpolator = DEFAULT_ANIMATION
    private var backgroundColor: Int = DEFAULT_OVERLAY_COLOR

    private var onSpotlightListener: OnSpotlightListener? = null

    fun addTargets(vararg targets: Target): Builder = apply {
      for (target in targets) {
        if (!this.targets.contains(target)) {
          this.targets.add(target)
        }
      }
    }

    fun addTargets(targets: List<Target>): Builder = apply {
      for (target in targets) {
        if (!this.targets.contains(target)) {
          this.targets.add(target)
        }
      }
    }

    fun setDuration(duration: Long): Builder = apply {
      this.duration = duration
    }

    fun setOverlayColor(@ColorRes backgroundColor: Int): Builder = apply {
      this.backgroundColor = backgroundColor
    }

    fun setAnimation(animation: TimeInterpolator): Builder = apply {
      this.animation = animation
    }

    fun setOnSpotlightListener(listener: OnSpotlightListener): Builder = apply {
      onSpotlightListener = listener
    }

    fun build(): Spotlight {
      return Spotlight(
          context = context,
          targets = targets,
          duration = duration,
          animation = animation,
          spotlightListener = onSpotlightListener,
          backgroundColor = backgroundColor
      )
    }
  }

  companion object {

    private val DEFAULT_DURATION = TimeUnit.SECONDS.toMillis(1)

    private val DEFAULT_ANIMATION = DecelerateInterpolator(2f)

    private val DEFAULT_OVERLAY_COLOR = R.color.background
  }
}
