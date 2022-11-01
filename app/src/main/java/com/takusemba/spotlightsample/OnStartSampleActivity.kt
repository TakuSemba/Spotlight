package com.takusemba.spotlightsample

import android.graphics.Typeface
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.takusemba.spotlight.Spotlight
import com.takusemba.spotlight.Target
import com.takusemba.spotlight.shape.Circle
import java.util.Random

class OnStartSampleActivity : AppCompatActivity(R.layout.activity_on_start_sample) {
  private val random by lazy { Random() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    findViewById<View>(R.id.add_new_dynamic_text).setOnClickListener { _ ->
      val (randomX, randomY) = getRandomWindowLocation()
      val textView = TextView(this@OnStartSampleActivity)
      textView.text = generateTextLabel()
      textView.x = randomX
      textView.y = randomY
      textView.setTextSize(36.0f)
      textView.setTypeface(Typeface.DEFAULT_BOLD)
      textView.setTextColor(0xffffffffL.toInt())
      findViewById<ViewGroup>(R.id.on_start_sample_container).addView(textView)
      showSpotlight(textView)
    }

    showSpotlight(findViewById<View>(R.id.text))
  }

  private fun showSpotlight(anchor: View) {
    val targetOverlay = layoutInflater.inflate(R.layout.layout_overlay_with_randomization, /* parent= */ null)
    val target = Target.Builder()
        .setAnchor(anchor)
        .setShape(Circle(100f))
        .setOverlay(targetOverlay)
        .build()
    val spotlight = Spotlight.Builder(this)
      .setTargets(listOf(target))
      .setBackgroundColorRes(R.color.spotlightBackground)
      .setDuration(1000L)
      .setAnimation(DecelerateInterpolator(2f))
      .build()

    targetOverlay.findViewById<View>(R.id.randomize_position_button).setOnClickListener {
      val (randomX, randomY) = getRandomWindowLocation()
      anchor.x = randomX
      anchor.y = randomY
      anchor.invalidate()
      anchor.requestLayout()
    }
    targetOverlay.findViewById<View>(R.id.close_spotlight).setOnClickListener { spotlight.finish() }

    spotlight.start()
  }

  private fun getRandomWindowLocation(): Pair<Float, Float> {
    val windowMetrics = DisplayMetrics().also { windowManager.defaultDisplay.getMetrics(it) }
    val x = randomizeWithinMargin(max = windowMetrics.widthPixels.toFloat(), margin = 50.0f)
    val y = randomizeWithinMargin(max = windowMetrics.heightPixels.toFloat(), margin = 50.0f)
    return x to y
  }

  private fun generateTextLabel(): String {
    return String(CharArray(random.nextInt(2) + 1) { random.nextInt(15).toString(radix = 16).single() })
  }

  private fun randomizeWithinMargin(min: Float = 0f, max: Float, margin: Float): Float =
    randomize(min + margin, max - margin)

  private fun randomize(min: Float, max: Float): Float = min + ((max - min) * random.nextFloat())
}
