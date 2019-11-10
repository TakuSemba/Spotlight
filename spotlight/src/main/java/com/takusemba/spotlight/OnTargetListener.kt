package com.takusemba.spotlight

import com.takusemba.spotlight.target.Target

/**
 * On Target State Changed Listener
 *
 * TODO this can be merged with [OnSpotlightListener]
 */
interface OnTargetListener {
  /**
   * Called when Target is started
   */
  fun onStarted(target: Target)

  /**
   * Called when Target is started
   */
  fun onEnded(target: Target)
}
