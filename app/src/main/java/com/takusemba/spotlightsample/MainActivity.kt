package com.takusemba.spotlightsample

import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import com.takusemba.spotlight.OnSpotlightListener
import com.takusemba.spotlight.OnTargetListener
import com.takusemba.spotlight.Spotlight
import com.takusemba.spotlight.Target
import com.takusemba.spotlight.shape.Circle

class MainActivity : AppCompatActivity() {

  private var currentToast: Toast? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    findViewById<View>(R.id.start).setOnClickListener {
      val targets = ArrayList<Target>()

      // first target
      val firstRoot = FrameLayout(this)
      val first = layoutInflater.inflate(R.layout.layout_target, firstRoot)
      val firstTarget = Target.Builder()
          .setAnchor(findViewById<View>(R.id.one))
          .setShape(Circle(100f))
          .setOverlay(first)
          .setOnTargetListener(object : OnTargetListener {
            override fun onStarted() {
              currentToast?.cancel()
              currentToast = makeText(this@MainActivity, "first target is started", LENGTH_SHORT)
              currentToast?.show()
            }

            override fun onEnded() {
              currentToast?.cancel()
              currentToast = makeText(this@MainActivity, "first target is ended", LENGTH_SHORT)
              currentToast?.show()
            }
          })
          .build()

      targets.add(firstTarget)

      // second target
      val secondRoot = FrameLayout(this)
      val second = layoutInflater.inflate(R.layout.layout_target, secondRoot)
      val secondTarget = Target.Builder()
          .setAnchor(findViewById<View>(R.id.two))
          .setShape(Circle(300f))
          .setOverlay(second)
          .setOnTargetListener(object : OnTargetListener {
            override fun onStarted() {
              currentToast?.cancel()
              currentToast = makeText(this@MainActivity, "second target is started", LENGTH_SHORT)
              currentToast?.show()
            }

            override fun onEnded() {
              currentToast?.cancel()
              currentToast = makeText(this@MainActivity, "second target is ended", LENGTH_SHORT)
              currentToast?.show()
            }
          })
          .build()

      targets.add(secondTarget)

      // third target
      val thirdRoot = FrameLayout(this)
      val third = layoutInflater.inflate(R.layout.layout_target, thirdRoot)
      val thirdTarget = Target.Builder()
          .setAnchor(findViewById<View>(R.id.three))
          .setShape(Circle(200f))
          .setOverlay(third)
          .setOnTargetListener(object : OnTargetListener {
            override fun onStarted() {
              currentToast?.cancel()
              currentToast = makeText(this@MainActivity, "third target is started", LENGTH_SHORT)
              currentToast?.show()
            }

            override fun onEnded() {
              currentToast?.cancel()
              currentToast = makeText(this@MainActivity, "third target is ended", LENGTH_SHORT)
              currentToast?.show()
            }
          })
          .build()

      targets.add(thirdTarget)

      // create spotlight
      val spotlight = Spotlight.Builder(this@MainActivity)
          .setTargets(targets)
          .setBackgroundColor(R.color.background)
          .setDuration(1000L)
          .setAnimation(DecelerateInterpolator(2f))
          .setOnSpotlightListener(object : OnSpotlightListener {
            override fun onStarted() {
              currentToast?.cancel()
              currentToast = makeText(this@MainActivity, "spotlight is started", LENGTH_SHORT)
              currentToast?.show()
            }

            override fun onEnded() {
              currentToast?.cancel()
              currentToast = makeText(this@MainActivity, "spotlight is ended", LENGTH_SHORT)
              currentToast?.show()
            }
          })
          .build()

      spotlight.start()

      val nextTarget = View.OnClickListener { spotlight.next() }

      val closeSpotlight = View.OnClickListener { spotlight.finish() }

      first.findViewById<View>(R.id.close_target).setOnClickListener(nextTarget)
      second.findViewById<View>(R.id.close_target).setOnClickListener(nextTarget)
      third.findViewById<View>(R.id.close_target).setOnClickListener(nextTarget)

      first.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
      second.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
      third.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
    }
  }
}
