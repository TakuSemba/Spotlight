package com.takusemba.spotlightsample

import android.graphics.PointF
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import com.takusemba.spotlight.OnSpotlightStateChangedListener
import com.takusemba.spotlight.OnTargetStateChangedListener
import com.takusemba.spotlight.Spotlight
import com.takusemba.spotlight.shape.Circle
import com.takusemba.spotlight.target.CustomTarget
import com.takusemba.spotlight.target.SimpleTarget
import com.takusemba.spotlight.target.Target
import java.util.*

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

            // first target
            val firstTarget = SimpleTarget.Builder(this@MainActivity)
                    .setPoint(oneX, oneY)
                    .setShape(Circle(100f))
                    .setTitle("first title")
                    .setDescription("first description")
                    .build()

            val two = findViewById<View>(R.id.two)
            val twoLocation = IntArray(2)
            two.getLocationInWindow(twoLocation)
            val point = PointF(twoLocation[0] + two.width / 2f, twoLocation[1] + two.height / 2f)

            // second target
            val secondTarget = SimpleTarget.Builder(this@MainActivity)
                    .setPoint(point)
                    .setShape(Circle(80f))
                    .setTitle("second title")
                    .setDescription("second description")
                    .setOnSpotlightStartedListener(object : OnTargetStateChangedListener<SimpleTarget> {
                        override fun onStarted(target: SimpleTarget) {
                            Toast.makeText(this@MainActivity, "target is started", Toast.LENGTH_SHORT).show()
                        }

                        override fun onEnded(target: SimpleTarget) {
                            Toast.makeText(this@MainActivity, "target is ended", Toast.LENGTH_SHORT).show()
                        }
                    })
                    .build()

            // third target
            val thirdTarget = SimpleTarget.Builder(this@MainActivity)
                    .setPoint(findViewById<View>(R.id.three))
                    .setShape(Circle(200f))
                    .setTitle("third title")
                    .setDescription("third description")
                    .build()

            // create spotlight
            Spotlight.with(this@MainActivity)
                    .setOverlayColor(R.color.background)
                    .setDuration(100L)
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
            val first = inflater.inflate(R.layout.layout_target, null)
            val firstTarget = CustomTarget.Builder(this@MainActivity)
                    .setPoint(findViewById<View>(R.id.one))
                    .setShape(Circle(100f))
                    .setOverlay(first)
                    .build()

            targets.add(firstTarget)

            // second target
            val second = inflater.inflate(R.layout.layout_target, null)
            val secondTarget = CustomTarget.Builder(this@MainActivity)
                    .setPoint(findViewById<View>(R.id.two))
                    .setShape(Circle(800f))
                    .setOverlay(second)
                    .build()

            targets.add(secondTarget)

            // third target
            val third = inflater.inflate(R.layout.layout_target, null)
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
