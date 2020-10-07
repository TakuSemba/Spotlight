package com.takusemba.spotlightsample

import android.app.Activity
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import com.takusemba.spotlight.OnSpotlightListener
import com.takusemba.spotlight.OnTargetListener
import com.takusemba.spotlight.Spotlight
import com.takusemba.spotlight.Target
import com.takusemba.spotlight.shape.Circle

class SpotLightUtils {
  companion object{

    fun showHintsToUser(activity: Activity, spotLayout:Int, vararg viewsList: View) {
      //iterate over all the views and show hint
      val targets = ArrayList<Target>()
      val views = ArrayList<View>()

      viewsList.forEach {
        val firstRoot = FrameLayout(activity)
        val first = activity.layoutInflater.inflate(spotLayout, firstRoot)
        val firstTarget = Target.Builder()
            .setAnchor(it)
            .setShape(Circle(100f))
            .setOverlay(first)
            .setOnTargetListener(object : OnTargetListener {
              override fun onStarted() {
              }

              override fun onEnded() {
              }
            })
            .build()
        targets.add(firstTarget)
        views.add(first)
      }

      // create spotlight
      val spotlight = Spotlight.Builder(activity)
          .setTargets(targets)
          .setBackgroundColor(R.color.spotlightBackground)
          .setDuration(1000L)
          .setAnimation(DecelerateInterpolator(2f))
          .setOnSpotlightListener(object : OnSpotlightListener {
            override fun onStarted() {

            }

            override fun onEnded() {

            }
          })
          .build()
      spotlight.start()
      val nextTarget = View.OnClickListener { spotlight.next() }
      val closeSpotlight = View.OnClickListener { spotlight.finish() }
      //listener
      views.forEach {
        it.findViewById<View>(R.id.close_target).setOnClickListener(nextTarget)
        it.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
      }

    }
  }
}