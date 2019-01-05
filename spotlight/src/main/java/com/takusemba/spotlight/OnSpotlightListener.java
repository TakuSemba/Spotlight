package com.takusemba.spotlight;

import com.takusemba.spotlight.target.Target;

/**
 * internal listener to notify when something happened on a {@link Target}
 */
interface OnSpotlightListener {

  /**
   * called when SpotlightView is clicked
   */
  void onSpotlightViewClicked();
}
