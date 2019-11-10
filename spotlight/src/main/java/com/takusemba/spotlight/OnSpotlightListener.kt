package com.takusemba.spotlight

import com.takusemba.spotlight.target.Target

/**
 * internal listener to notify when something happened on a [Target]
 * TODO move to SpotlightView
 */
internal interface OnSpotlightListener {

  /**
   * called when SpotlightView is clicked
   */
  fun onSpotlightViewClicked()
}
