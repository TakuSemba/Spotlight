package com.takusemba.spotlightsample

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.takusemba.spotlight.OnSpotlightStateChangedListener
import com.takusemba.spotlight.OnTargetStateChangedListener
import com.takusemba.spotlight.Spotlight
import com.takusemba.spotlight.shape.Circle
import com.takusemba.spotlight.shape.Padding
import com.takusemba.spotlight.shape.RoundedRectangle
import com.takusemba.spotlight.target.CustomTarget
import com.takusemba.spotlight.target.SimpleTarget
import com.takusemba.spotlight.target.Target
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

  private var spotlight: Spotlight? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val rootView = findViewById<ViewGroup>(android.R.id.content)

    findViewById<View>(R.id.simple_target).setOnClickListener {
      val one = findViewById<View>(R.id.one)
      val viewOneBounds = Rect()
      one.getDrawingRect(viewOneBounds)
      rootView.offsetDescendantRectToMyCoords(one, viewOneBounds)

      // first target
      val firstTarget = SimpleTarget.Builder(this@MainActivity)
          .setRect(viewOneBounds) // Same size as the view
          // Defaults to a circle
          .setTitle("Simple 1")
          .setDescription("Fits a view")
          .setOverlayPadding(100, viewOneBounds.bottom + 100)
          .build()

      val two = findViewById<View>(R.id.two)
      val viewTwoBounds = Rect()
      two.getDrawingRect(viewTwoBounds)
      rootView.offsetDescendantRectToMyCoords(two, viewTwoBounds)

      // second target
      val secondTarget = SimpleTarget.Builder(this@MainActivity)
          .setRect(viewTwoBounds)
          .setShape(Circle(50)) // Add extra padding around spotlight
          .setTitle("Simple 2")
          .setDescription("Add 50 padding to circle")
          .setOverlayPadding(Padding(100, viewTwoBounds.bottom + 100))
          .setTargetListener(object : OnTargetStateChangedListener<SimpleTarget> {
            override fun onStarted(target: SimpleTarget) {
              Toast.makeText(this@MainActivity, "target is started", Toast.LENGTH_SHORT).show()
            }

            override fun onEnded(target: SimpleTarget) {
              Toast.makeText(this@MainActivity, "target is ended", Toast.LENGTH_SHORT).show()
            }
          })
          .build()

      val threeView = findViewById<View>(R.id.three)
      val viewThreeBounds = Rect()
      threeView.getDrawingRect(viewThreeBounds)
      rootView.offsetDescendantRectToMyCoords(threeView, viewThreeBounds)

      // third target
      val thirdTarget = SimpleTarget.Builder(this@MainActivity)
          .setRectFromView(threeView)
          .setShape(RoundedRectangle(Padding(-10, -50), 25f))
          .setTitle("Simple 3")
          .setDescription("Negative padding on Rectangle (-10x, -50y)")
          .setOverlayPadding(100, viewThreeBounds.top - 300)
          .setTargetListener(object : OnTargetStateChangedListener<SimpleTarget> {
            override fun onStarted(target: SimpleTarget?) {
            }

            override fun onEnded(target: SimpleTarget?) {
              val viewFour = findViewById<View>(R.id.four)
              viewFour.visibility = View.VISIBLE
              viewFour
                  .viewTreeObserver
                  .addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                      if (viewFour.visibility == View.VISIBLE) {
                        // 4th view is visible now
                        spotlight?.startNextTarget()
                        viewFour.viewTreeObserver.removeGlobalOnLayoutListener(this)
                      }
                    }
                  })
            }
          })
          .build()

      // fourth target
      val fourthTarget = SimpleTarget.Builder(this@MainActivity)
          .setRectSupplierFromView(R.id.four)
          .setShape(RoundedRectangle(25f))
          .setTitle("Simple 4")
          .setDescription("Wait for view to become visible before showing tutorial")
          .setOverlayPadding(100, 500)
          .setAutoStart(false)
          .setTargetListener(object : OnTargetStateChangedListener<SimpleTarget> {
            override fun onEnded(target: SimpleTarget?) {
              findViewById<View>(R.id.four).visibility = View.GONE
            }

            override fun onStarted(target: SimpleTarget?) {}
          }).build()

      // create spotlight
      spotlight = Spotlight.with(this@MainActivity)
          .setOverlayColor(R.color.background)
          .setDuration(1000L)
          .setAnimation(DecelerateInterpolator(2f))
          .setTargets(firstTarget, secondTarget, thirdTarget, fourthTarget)
          .setClosedOnTouchedOutside(true)
          .setOnSpotlightStateListener(object : OnSpotlightStateChangedListener {
            override fun onStarted() {
              Toast.makeText(this@MainActivity, "spotlight is started", Toast.LENGTH_SHORT)
                  .show()
            }

            override fun onEnded() {
              Toast.makeText(this@MainActivity, "spotlight is ended", Toast.LENGTH_SHORT).show()
            }
          }).apply { start() }
    }

    findViewById<View>(R.id.custom_target).setOnClickListener {
      val inflater = LayoutInflater.from(this@MainActivity)

      val targets = ArrayList<Target>()

      // first target
      val firstRoot = FrameLayout(this)
      val first = inflater.inflate(R.layout.layout_target, firstRoot)
      val firstTarget = CustomTarget.Builder(this@MainActivity)
          .setRectSupplier {
            //Defer rect calculation until target starts
            val view = findViewById<View>(R.id.one)
            val viewBounds = Rect()
            view.getDrawingRect(viewBounds)
            val root = findViewById<ViewGroup>(android.R.id.content)
            root.offsetDescendantRectToMyCoords(view, viewBounds)
            viewBounds
          }
          .setShape(RoundedRectangle(Padding(50, 50), 10f))
          .setOverlay(first)
          .build()

      targets.add(firstTarget)

      // second target
      val secondRoot = FrameLayout(this)
      val second = inflater.inflate(R.layout.layout_target, secondRoot)
      val secondTarget = CustomTarget.Builder(this@MainActivity)
          .setRectSupplierFromView(
              R.id.two) // Defer rect calculation until target starts, using Resource ID
          .setShape(RoundedRectangle(Padding(10, 10)))
          .setOverlay(second)
          .build()

      targets.add(secondTarget)

      // third target
      val thirdRoot = FrameLayout(this)
      val third = inflater.inflate(R.layout.layout_target, thirdRoot)
      val thirdTarget = CustomTarget.Builder(this@MainActivity)
          // Defer rect calculation until target start, but using a placeholder in the layout_target
          .setRectSupplierFromView(R.id.spotlight_placeholder)
          .setShape(RoundedRectangle(10f))
          .setOverlay(third)
          .build()

      targets.add(thirdTarget)

      // create spotlight
      spotlight = Spotlight.with(this@MainActivity)
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
          }).apply { start() }

      val closeTarget = View.OnClickListener { spotlight?.closeCurrentTarget() }

      val closeSpotlight = View.OnClickListener { spotlight?.closeSpotlight() }

      first.findViewById<View>(R.id.close_target).setOnClickListener(closeTarget)
      second.findViewById<View>(R.id.close_target).setOnClickListener(closeTarget)
      third.findViewById<View>(R.id.close_target).setOnClickListener(closeTarget)

      first.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
      second.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
      third.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
    }
  }
}
