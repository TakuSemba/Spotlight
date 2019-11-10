package com.takusemba.spotlight

/**
 * On LegacyTarget State Changed Listener
 */
interface OnTargetListener {
  /**
   * Called when LegacyTarget is started
   */
  fun onStarted(target: Target)

  /**
   * Called when LegacyTarget is started
   */
  fun onEnded(target: Target)
}
