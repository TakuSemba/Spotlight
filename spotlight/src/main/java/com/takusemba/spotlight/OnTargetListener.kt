package com.takusemba.spotlight

import com.takusemba.spotlight.target.Target

/**
 * On Target State Changed Listener
 *
 * TODO this can be merged with [OnSpotlightListener]
 */
interface OnTargetListener<T : Target> {
  /**
   * Called when Target is started
   */
  fun onStarted(target: T)

  /**
   * Called when Target is started
   */
  fun onEnded(target: T)
}
