package com.takusemba.spotlight

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.app.Activity
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
    private val activity: Activity,
    private val targets: Array<Target>,
    private val duration: Long,
    private val animation: TimeInterpolator,
    @ColorRes private val backgroundColor: Int,
    private val container: ViewGroup,
    private val spotlightListener: OnSpotlightListener?
) {

  private val spotlightView = SpotlightView(activity, null, 0, backgroundColor)

  private var currentIndex = NO_POSITION

  init {
    container.addView(spotlightView, MATCH_PARENT, MATCH_PARENT)
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
    closeSpotlight()
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
    if (currentIndex == NO_POSITION) {
      val target = targets[index]
      currentIndex = index
      spotlightView.startTarget(target, object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animation: Animator) {
          target.listener?.onStarted()
        }
      })
    } else {
      spotlightView.closeTarget(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
          val previousIndex = currentIndex
          val previousTarget = targets[previousIndex]
          previousTarget.listener?.onEnded()
          if (index < targets.size) {
            val target = targets[index]
            currentIndex = index
            spotlightView.startTarget(target, object : AnimatorListenerAdapter() {
              override fun onAnimationStart(animation: Animator) {
                target.listener?.onStarted()
              }
            })
          } else {
            closeSpotlight()
          }
        }
      })
    }
  }

  /**
   * hide Spotlight
   */
  private fun closeSpotlight() {
    spotlightView.closeSpotlight(duration, animation, object : AnimatorListenerAdapter() {
      override fun onAnimationEnd(animation: Animator) {
        spotlightView.removeAllViews()
        container.removeView(spotlightView)
        spotlightListener?.onEnded()
      }
    })
  }

  companion object {

    private const val NO_POSITION = -1
  }

  class Builder(private val activity: Activity) {

    private var targets: Array<Target>? = null
    private var duration: Long = DEFAULT_DURATION
    private var animation: TimeInterpolator = DEFAULT_ANIMATION
    @ColorRes private var backgroundColor: Int = DEFAULT_OVERLAY_COLOR
    private var container: ViewGroup? = null
    private var listener: OnSpotlightListener? = null

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

    fun setContainer(container: ViewGroup) = apply {
      this.container = container
    }

    fun setOnSpotlightListener(listener: OnSpotlightListener): Builder = apply {
      this.listener = listener
    }

    fun build(): Spotlight {

      val targets = requireNotNull(targets) { "targets should not be null. " }
      val container = container ?: activity.window.decorView as ViewGroup

      return Spotlight(
          activity = activity,
          targets = targets,
          duration = duration,
          animation = animation,
          backgroundColor = backgroundColor,
          container = container,
          spotlightListener = listener
      )
    }

    companion object {

      private val DEFAULT_DURATION = TimeUnit.SECONDS.toMillis(1)

      private val DEFAULT_ANIMATION = DecelerateInterpolator(2f)

      @ColorRes private val DEFAULT_OVERLAY_COLOR = R.color.background
    }
  }
}
