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
import java.util.ArrayList
import java.util.concurrent.TimeUnit

/**
 * Spotlight that holds all the [Target]s and show and hide [Target] properly, and show
 * and hide [SpotlightView] properly.
 */
class Spotlight<T> private constructor(private val context: Context) where T : Target<T> {

  private var spotlightView: SpotlightView? = null

  private var targets: ArrayList<T>? = null
  private var duration: Long = DEFAULT_DURATION
  private var animation: TimeInterpolator = DEFAULT_ANIMATION
  private var spotlightListener: OnSpotlightStateChangedListener? = null
  @ColorRes private var overlayColor: Int = DEFAULT_OVERLAY_COLOR
  private var isClosedOnTouchedOutside: Boolean = true

  /**
   * sets [Target]s to Spotlight
   *
   * @param targets targets to show
   * @return the Spotlight
   */
  @SafeVarargs fun setTargets(vararg targets: T): Spotlight<T> {
    this.targets = ArrayList(listOf(*targets))

    return this
  }

  /**
   * sets [Target]s to Spotlight
   *
   * @param targets targets as ArrayList to show
   * @return the Spotlight
   */
  fun setTargets(targets: ArrayList<T>): Spotlight<T> {
    this.targets = targets

    return this
  }

  /**
   * sets spotlight background color to Spotlight
   *
   * @param overlayColor background color to be used for the spotlight overlay
   * @return the Spotlight
   */
  fun setOverlayColor(@ColorRes overlayColor: Int): Spotlight<T> {
    this.overlayColor = overlayColor
    return this
  }

  /**
   * sets duration to [Target] Animation
   *
   * @param duration duration of Target Animation
   * @return the Spotlight
   */
  fun setDuration(duration: Long): Spotlight<T> {
    this.duration = duration
    return this
  }

  /**
   * sets interpolator to [Target] Animation
   *
   * @param animation type of Target Animation
   * @return the Spotlight
   */
  fun setAnimation(animation: TimeInterpolator): Spotlight<T> {
    this.animation = animation
    return this
  }

  /**
   * Sets [OnSpotlightStateChangedListener]
   *
   * @param listener OnSpotlightEndedListener of Spotlight
   * @return This Spotlight
   */
  fun setOnSpotlightStateListener(
      listener: OnSpotlightStateChangedListener
  ): Spotlight<T> {
    spotlightListener = listener
    return this
  }

  /**
   * Sets if Spotlight closes Target if touched outside
   *
   * @param isClosedOnTouchedOutside OnSpotlightEndedListener of Spotlight
   * @return This Spotlight
   */
  fun setClosedOnTouchedOutside(isClosedOnTouchedOutside: Boolean): Spotlight<T> {
    this.isClosedOnTouchedOutside = isClosedOnTouchedOutside
    return this
  }

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
    val spotlightView = SpotlightView(context!!, overlayColor, object : OnSpotlightListener {
      override fun onSpotlightViewClicked() {
        if (isClosedOnTouchedOutside) {
          finishTarget()
        }
      }
    })
    this.spotlightView = spotlightView
    (decorView as ViewGroup).addView(spotlightView)
    startSpotlight()
  }

  /**
   * show Target
   */
  private fun startTarget() {
    if (targets != null && targets!!.size > 0 && spotlightView != null) {
      val target = targets!![0]
      val spotlightView = spotlightView
      spotlightView!!.removeAllViews()
      spotlightView.addView(target.overlay)
      spotlightView.turnUp(target, object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animation: Animator) {
          if (target.listener != null) {
            target.listener?.onStarted(target)
          }
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
        if (spotlightListener != null) spotlightListener!!.onStarted()
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
    if (targets != null && targets!!.size > 0 && spotlightView != null) {
      spotlightView!!.turnDown(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
          if (!targets!!.isEmpty()) {
            val target = targets!!.removeAt(0)
            if (target.listener != null) {
              target.listener?.onEnded(target)
            }
            if (targets!!.size > 0) {
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
          if (spotlightListener != null) spotlightListener!!.onEnded()
        }
      }
    })
  }

  companion object {

    @ColorRes private val DEFAULT_OVERLAY_COLOR = R.color.background
    private val DEFAULT_DURATION = TimeUnit.SECONDS.toMillis(1)
    private val DEFAULT_ANIMATION = DecelerateInterpolator(2f)

    fun <T : Target<T>> with(context: Context): Spotlight<T> {
      return Spotlight(context)
    }
  }
}
