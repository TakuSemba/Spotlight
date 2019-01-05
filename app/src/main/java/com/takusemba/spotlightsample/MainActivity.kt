package com.takusemba.spotlightsample

import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.takusemba.spotlight.OnSpotlightStateChangedListener
import com.takusemba.spotlight.OnTargetStateChangedListener
import com.takusemba.spotlight.Spotlight
import com.takusemba.spotlight.shape.Circle
import com.takusemba.spotlight.shape.RoundedRectangle
import com.takusemba.spotlight.target.CustomTarget
import com.takusemba.spotlight.target.SimpleTarget
import com.takusemba.spotlight.target.Target
import java.util.ArrayList

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
      val firstTarget = SimpleTarget.Builder(this@MainActivity)
          .setPoint(oneX, oneY)
          .setShape(Circle(oneRadius))
          .setTitle("first title")
          .setDescription("first description")
          .setOverlayPoint(100f, oneY + oneRadius + 100f)
          .build()

      val two = findViewById<View>(R.id.two)
      val twoLocation = IntArray(2)
      two.getLocationInWindow(twoLocation)
      val twoPoint = PointF(twoLocation[0] + two.width / 2f, twoLocation[1] + two.height / 2f)
      val twoRadius = 100f

      // second target
      val secondTarget = SimpleTarget.Builder(this@MainActivity)
          .setPoint(twoPoint)
          .setShape(Circle(twoRadius))
          .setTitle("second title")
          .setDescription("second description")
          .setOverlayPoint(PointF(100f, twoPoint.y + twoRadius + 100f))
          .setOnSpotlightStartedListener(object : OnTargetStateChangedListener<SimpleTarget> {
            override fun onStarted(target: SimpleTarget) {
              Toast.makeText(this@MainActivity, "target is started", Toast.LENGTH_SHORT).show()
            }

            override fun onEnded(target: SimpleTarget) {
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
      val thirdTarget = SimpleTarget.Builder(this@MainActivity)
          .setPoint(threeView)
          .setShape(RoundedRectangle(threeWidth, threeHeight, 25f))
          .setTitle("third title")
          .setDescription("third description")
          .setOverlayPoint(100f, y - threeHeight - 300)
          .build()

      // create spotlight
      Spotlight.with(this@MainActivity)
          .setOverlayColor(R.color.background)
          .setDuration(1000L)
          .setAnimation(DecelerateInterpolator(2f))
          .setTargets(firstTarget, secondTarget, thirdTarget)
          .setClosedOnTouchedOutside(true)
          .setOnSpotlightStateListener(object : OnSpotlightStateChangedListener {
            override fun onStarted() {
              Toast.makeText(this@MainActivity, "spotlight is started", Toast.LENGTH_SHORT)
                  .show()
            }

            override fun onEnded() {
              Toast.makeText(this@MainActivity, "spotlight is ended", Toast.LENGTH_SHORT).show()
            }
          })
          .start()
    }

    findViewById<View>(R.id.custom_target).setOnClickListener { _ ->
      val inflater = LayoutInflater.from(this@MainActivity)

      val targets = ArrayList<Target>()

      // first target
      val firstRoot = FrameLayout(this)
      val first = inflater.inflate(R.layout.layout_target, firstRoot)
      val firstTarget = CustomTarget.Builder(this@MainActivity)
          .setPoint(findViewById<View>(R.id.one))
          .setShape(Circle(100f))
          .setOverlay(first)
          .build()

      targets.add(firstTarget)

      // second target
      val secondRoot = FrameLayout(this)
      val second = inflater.inflate(R.layout.layout_target, secondRoot)
      val secondTarget = CustomTarget.Builder(this@MainActivity)
          .setPoint(findViewById<View>(R.id.two))
          .setShape(Circle(300f))
          .setOverlay(second)
          .build()

      targets.add(secondTarget)

      // third target
      val thirdRoot = FrameLayout(this)
      val third = inflater.inflate(R.layout.layout_target, thirdRoot)
      val thirdTarget = CustomTarget.Builder(this@MainActivity)
          .setPoint(findViewById<View>(R.id.three))
          .setShape(Circle(200f))
          .setOverlay(third)
          .build()

      targets.add(thirdTarget)

      // create spotlight
      val spotlight = Spotlight.with(this@MainActivity)
          .setOverlayColor(R.color.background)
          .setDuration(1000L)
          .setAnimation(DecelerateInterpolator(2f))
          .setTargets(targets)
          .setClosedOnTouchedOutside(false)
          .setOnSpotlightStateListener(object : OnSpotlightStateChangedListener {
            override fun onStarted() {
              Toast.makeText(this@MainActivity, "spotlight is started", Toast.LENGTH_SHORT).show()
            }

            override fun onEnded() {
              Toast.makeText(this@MainActivity, "spotlight is ended", Toast.LENGTH_SHORT).show()
            }
          })
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
