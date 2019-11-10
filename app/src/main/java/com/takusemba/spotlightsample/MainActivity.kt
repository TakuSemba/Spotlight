package com.takusemba.spotlightsample

import android.graphics.PointF
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.takusemba.spotlight.OnSpotlightListener
import com.takusemba.spotlight.OnTargetListener
import com.takusemba.spotlight.Spotlight
import com.takusemba.spotlight.Target
import com.takusemba.spotlight.shape.Circle
import com.takusemba.spotlight.shape.RoundedRectangle

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    findViewById<View>(R.id.simple_target).setOnClickListener {
      val one = findViewById<View>(R.id.one)
      val oneLocation = IntArray(2)
      one.getLocationInWindow(oneLocation)
      val oneX = oneLocation[0] + one.width / 2f
      val oneY = oneLocation[1] + one.height / 2f
      val oneRadius = 100f

      // first target
      val firstRoot = FrameLayout(this)
      val first = layoutInflater.inflate(R.layout.layout_target, firstRoot)
      val firstTarget = Target.Builder()
          .setAnchor(oneX, oneY)
          .setShape(Circle(oneRadius))
          .setOverlay(first)
          .build()

      val two = findViewById<View>(R.id.two)
      val twoLocation = IntArray(2)
      two.getLocationInWindow(twoLocation)
      val twoPoint = PointF(twoLocation[0] + two.width / 2f, twoLocation[1] + two.height / 2f)
      val twoRadius = 100f

      // second target
      val secondRoot = FrameLayout(this)
      val second = layoutInflater.inflate(R.layout.layout_target, secondRoot)
      val secondTarget = Target.Builder()
          .setAnchor(twoPoint)
          .setShape(Circle(twoRadius))
          .setOverlay(second)
          .setOnTargetListener(object : OnTargetListener {
            override fun onStarted(target: Target) {
              Toast.makeText(this@MainActivity, "target is started", Toast.LENGTH_SHORT).show()
            }

            override fun onEnded(target: Target) {
              Toast.makeText(this@MainActivity, "target is ended", Toast.LENGTH_SHORT).show()
            }
          })
          .build()

      val threeWidth = 300f
      val threeHeight = 300f
      val threeView = findViewById<View>(R.id.three)
      val location = IntArray(2)
      threeView.getLocationInWindow(location)
      val y = location[1] + threeView.height / 2

      // third target
      val thirdRoot = FrameLayout(this)
      val third = layoutInflater.inflate(R.layout.layout_target, thirdRoot)
      val thirdTarget = Target.Builder()
          .setAnchor(threeView)
          .setShape(RoundedRectangle(threeWidth, threeHeight, 25f))
          .setOverlay(third)
          .build()

      // create spotlight
      Spotlight.Builder(this@MainActivity)
          .addTargets(firstTarget, secondTarget, thirdTarget)
          .setOverlayColor(R.color.background)
          .setDuration(1000L)
          .setAnimation(DecelerateInterpolator(2f))
          .setClosedOnTouchedOutside(true)
          .setOnSpotlightListener(object : OnSpotlightListener {
            override fun onStarted() {
              Toast.makeText(this@MainActivity, "spotlight is started", Toast.LENGTH_SHORT).show()
            }

            override fun onEnded() {
              Toast.makeText(this@MainActivity, "spotlight is ended", Toast.LENGTH_SHORT).show()
            }
          })
          .build()
          .start()
    }

    findViewById<View>(R.id.custom_target).setOnClickListener {
      val targets = ArrayList<Target>()

      // first target
      val firstRoot = FrameLayout(this)
      val first = layoutInflater.inflate(R.layout.layout_target, firstRoot)
      val firstTarget = Target.Builder()
          .setAnchor(findViewById<View>(R.id.one))
          .setShape(Circle(100f))
          .setOverlay(first)
          .build()

      targets.add(firstTarget)

      // second target
      val secondRoot = FrameLayout(this)
      val second = layoutInflater.inflate(R.layout.layout_target, secondRoot)
      val secondTarget = Target.Builder()
          .setAnchor(findViewById<View>(R.id.two))
          .setShape(Circle(300f))
          .setOverlay(second)
          .build()

      targets.add(secondTarget)

      // third target
      val thirdRoot = FrameLayout(this)
      val third = layoutInflater.inflate(R.layout.layout_target, thirdRoot)
      val thirdTarget = Target.Builder()
          .setAnchor(findViewById<View>(R.id.three))
          .setShape(Circle(200f))
          .setOverlay(third)
          .build()

      targets.add(thirdTarget)

      // create spotlight
      val spotlight = Spotlight.Builder(this@MainActivity)
          .addTargets(targets)
          .setOverlayColor(R.color.background)
          .setDuration(1000L)
          .setAnimation(DecelerateInterpolator(2f))
          .setClosedOnTouchedOutside(false)
          .setOnSpotlightListener(object : OnSpotlightListener {
            override fun onStarted() {
              Toast.makeText(this@MainActivity, "spotlight is started", Toast.LENGTH_SHORT).show()
            }

            override fun onEnded() {
              Toast.makeText(this@MainActivity, "spotlight is ended", Toast.LENGTH_SHORT).show()
            }
          })
          .build()

      spotlight.start()

      val closeTarget = View.OnClickListener { spotlight.closeCurrentTarget() }

      val closeSpotlight = View.OnClickListener { spotlight.closeSpotlight() }

      first.findViewById<View>(R.id.close_target).setOnClickListener(closeTarget)
      second.findViewById<View>(R.id.close_target).setOnClickListener(closeTarget)
      third.findViewById<View>(R.id.close_target).setOnClickListener(closeTarget)

      first.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
      second.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
      third.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
    }
  }
}
