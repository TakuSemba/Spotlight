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
    private val targets: Array<Target>,
    private val duration: Long,
    private val animation: TimeInterpolator,
    private val spotlightListener: OnSpotlightListener?,
    private val backgroundColor: Int
) {

  private val spotlightView = SpotlightView(context, null, 0, backgroundColor)

  private var currentIndex = NO_POSITION

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

  fun show(index: Int) {
    showTarget(index)
  }

  /**
   * close the current [Target]
   */
  fun next() {
    showTarget()
  }

  fun previous() {
    showTarget(currentIndex - 1)
  }

  /**
   * close the [Spotlight]
   */
  fun close() {
    finishSpotlight()
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
        showTarget(0)
      }
    })
  }

  private fun showTarget(index: Int = currentIndex + 1) {
    if (currentIndex == index) {
      return
    }
    if (currentIndex == NO_POSITION && index == 0) {
      val target = targets[index]
      currentIndex = index
      spotlightView.turnUp(target, object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animation: Animator) {
          target.listener?.onStarted()
        }
      })
    } else {
      spotlightView.turnDown(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
          val previousIndex = currentIndex
          val previousTarget = targets[previousIndex]
          previousTarget.listener?.onEnded()
          if (index < targets.size) {
            val target = targets[index]
            currentIndex = index
            spotlightView.turnUp(target, object : AnimatorListenerAdapter() {
              override fun onAnimationStart(animation: Animator) {
                target.listener?.onStarted()
              }
            })
          } else {
            finishSpotlight()
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
          spotlightView.removeAllViews()
          val decorView = activity.window.decorView
          (decorView as ViewGroup).removeView(spotlightView)
          spotlightListener?.onEnded()
        }
      }
    })
  }

  companion object {

    private const val NO_POSITION = -1
  }

  class Builder(private val context: Context) {

    private var targets: Array<Target>? = null
    private var duration: Long = DEFAULT_DURATION
    private var animation: TimeInterpolator = DEFAULT_ANIMATION
    private var backgroundColor: Int = DEFAULT_OVERLAY_COLOR
    private var onSpotlightListener: OnSpotlightListener? = null

    fun setTargets(vararg targets: Target): Builder = apply {
      this.targets = arrayOf(*targets)
    }

    fun setTargets(targets: List<Target>): Builder = apply {
      this.targets = targets.toTypedArray()
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

      val targets = requireNotNull(targets) { "targets should not be null. " }

      return Spotlight(
          context = context,
          targets = targets,
          duration = duration,
          animation = animation,
          spotlightListener = onSpotlightListener,
          backgroundColor = backgroundColor
      )
    }

    companion object {

      private val DEFAULT_DURATION = TimeUnit.SECONDS.toMillis(1)

      private val DEFAULT_ANIMATION = DecelerateInterpolator(2f)

      private val DEFAULT_OVERLAY_COLOR = R.color.background
    }
  }
}
