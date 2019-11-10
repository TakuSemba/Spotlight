package com.takusemba.spotlight.target

import android.animation.TimeInterpolator
import android.graphics.PointF
import android.view.View
import com.takusemba.spotlight.OnTargetStateChangedListener
import com.takusemba.spotlight.shape.Circle
import com.takusemba.spotlight.shape.Shape

/**
 * Target is used to light up a certain area.
 * The shape of the Target is customizable using [Shape] class.
 * If you want to show circle shape of a Target, set [Circle] class.
 */
interface Target<T> where T : Target<T> {
  /**
   * gets shape of this Target
   *
   * @return shape of this Target
   */
  val shape: Shape
  /**
   * gets the point of this Target
   *
   * @return the point of this Target
   */
  val point: PointF
  /**
   * gets the view of this Target
   *
   * @return the view of this Target
   */
  val overlay: View
  /**
   * gets duration of this Target
   *
   * @return duration of this Target
   */
  val duration: Long
  /**
   * gets animation of this Target
   *
   * @return animation of this Target
   */
  val animation: TimeInterpolator
  /**
   * gets the listener of this Target
   *
   * @return the listener of this Target
   */
  val listener: OnTargetStateChangedListener<T>?
}