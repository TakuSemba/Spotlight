package com.takusemba.spotlight

import android.app.Activity

interface SpotlightCoordinator {
  var spotlight: Spotlight?
  fun initSpotlight(activity: Activity)
  fun setTargets(targets: List<Target>) = spotlight?.setTargets(targets)
  fun isSpotlightRunning() = spotlight?.isRunning() == true
  fun restartSpotlight() = spotlight?.apply {
    if (isRunning()) finish()
    start()
  }
  fun next() = spotlight?.next()
  fun previous() = spotlight?.previous()
  fun show(index: Int) = spotlight?.show(index)
  fun stop() = spotlight?.finish()
}
