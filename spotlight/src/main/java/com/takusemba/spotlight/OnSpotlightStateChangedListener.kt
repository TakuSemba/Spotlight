package com.takusemba.spotlight

/**
 * On Spotlight Ended Listener
 */
interface OnSpotlightStateChangedListener {

  /**
   * Called when Spotlight is started
   */
  fun onStarted()

  /**
   * Called when Spotlight is ended
   */
  fun onEnded()
}
