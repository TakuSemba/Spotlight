package com.takusemba.spotlight

/**
 * Listener to notify the state of Spotlight.
 */
interface OnSpotlightListener {

  /**
   * Called when Spotlight is started
   */
  fun onStarted()

  /**
   * Called when Spotlight is ended
   */
  fun onEnded()
}
