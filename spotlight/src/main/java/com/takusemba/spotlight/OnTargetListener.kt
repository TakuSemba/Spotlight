package com.takusemba.spotlight

/**
 * On Target State Changed Listener
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
