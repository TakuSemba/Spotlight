package com.takusemba.spotlight

/**
 * On Spotlight Ended Listener
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
