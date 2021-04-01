package com.takusemba.spotlightsample

import android.app.Activity
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.takusemba.spotlight.OnSpotlightListener
import com.takusemba.spotlight.Spotlight
import com.takusemba.spotlight.SpotlightCoordinator

class MultiFragmentSampleActivity : AppCompatActivity(
    R.layout.activity_multi_fragment_sample), SpotlightCoordinator {
  interface SpotlightFragment {
    fun reinitSpotlight()
  }

  private val onBackStackChangedListener = FragmentManager.OnBackStackChangedListener {
    (supportFragmentManager.fragments.last() as SpotlightFragment).reinitSpotlight()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    supportFragmentManager.addOnBackStackChangedListener(onBackStackChangedListener)

    initSpotlight(this)

    supportFragmentManager.beginTransaction()
        .add(R.id.rootViewActivityMultiFragmentSample, MultiFragmentSampleFragment1())
        .commit()
  }

  override var spotlight: Spotlight? = null

  override fun initSpotlight(activity: Activity) {
    Spotlight.Builder(activity)
        .setBackgroundColorRes(R.color.spotlightBackground)
        .setDuration(1000L)
        .setAnimation(DecelerateInterpolator(2f))
        .setOnSpotlightListener(object : OnSpotlightListener {
          override fun onStarted() {
          }

          override fun onEnded() {
          }
        }
        )
        .setSpotlightCoordinator(activity as SpotlightCoordinator)
        .build()
  }
}