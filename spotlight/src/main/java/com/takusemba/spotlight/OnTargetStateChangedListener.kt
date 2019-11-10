package com.takusemba.spotlight

import com.takusemba.spotlight.target.Target

/**
 * On Target State Changed Listener
 */
interface OnTargetStateChangedListener<T : Target<T>> {
  /**
   * Called when Target is started
   */
  fun onStarted(target: T)

  /**
   * Called when Target is started
   */
  fun onEnded(target: T)
}
