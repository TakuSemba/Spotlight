package com.takusemba.spotlightsample

import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.takusemba.spotlight.OnSpotlightListener
import com.takusemba.spotlight.OnTargetListener
import com.takusemba.spotlight.OverlayAlignment
import com.takusemba.spotlight.Spotlight
import com.takusemba.spotlight.Target
import com.takusemba.spotlight.shape.RoundedRectangle

class FragmentSampleFragment2 : Fragment(R.layout.fragment_fragment_sample) {

  private var currentToast: Toast? = null

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    view.findViewById<View>(R.id.start).setOnClickListener { startButton ->
      val targets = ArrayList<Target>()

      val overlayHeight = resources.getDimensionPixelSize(R.dimen.space_128dp)

      // first target
      val firstRoot = FrameLayout(requireContext())
      val first = layoutInflater.inflate(R.layout.layout_target2, firstRoot)
      val anchor1 = view.findViewById<View>(R.id.one)
      val firstTarget = Target.Builder()
          .setAnchor(anchor1)
          .setShape(RoundedRectangle(1.5f*anchor1.height.toFloat(), 2*anchor1.width.toFloat(), 15f))
          .setOverlay(first, overlayHeight = overlayHeight.toFloat())
          .setOverlayAlignment(OverlayAlignment.TOP_LEFT)
          .setOnTargetListener(object : OnTargetListener {
            override fun onStarted() {
              currentToast?.cancel()
              currentToast = Toast.makeText(
                  requireContext(),
                  "first target is started",
                  Toast.LENGTH_SHORT
              )
              currentToast?.show()
            }

            override fun onEnded() {
              currentToast?.cancel()
              currentToast = Toast.makeText(
                  requireContext(),
                  "first target is ended",
                  Toast.LENGTH_SHORT
              )
              currentToast?.show()
            }
          })
          .build()

      targets.add(firstTarget)

      // second target
      val secondRoot = FrameLayout(requireActivity())
      val second = layoutInflater.inflate(R.layout.layout_target2, secondRoot)
      val anchor2 = view.findViewById<View>(R.id.two)
      val secondTarget = Target.Builder()
          .setAnchor(anchor2)
          .setShape(RoundedRectangle(1.5f*anchor2.height.toFloat(), 2*anchor2.width.toFloat(), 15f))
          .setOverlay(second,overlayHeight = overlayHeight.toFloat())
//          .setOverlayAlignment(OverlayAlignment.TOP_LEFT)
          .setOnTargetListener(object : OnTargetListener {
            override fun onStarted() {
              currentToast?.cancel()
              currentToast = Toast.makeText(
                  requireContext(),
                  "second target is started",
                  Toast.LENGTH_SHORT
              )
              currentToast?.show()
            }

            override fun onEnded() {
              currentToast?.cancel()
              currentToast = Toast.makeText(
                  requireContext(),
                  "second target is ended",
                  Toast.LENGTH_SHORT
              )
              currentToast?.show()
            }
          })
          .build()

      targets.add(secondTarget)

      // third target
      val thirdRoot = FrameLayout(requireContext())
      val third = layoutInflater.inflate(R.layout.layout_target2, thirdRoot)
      val anchor3 = view.findViewById<View>(R.id.three)
      val thirdTarget = Target.Builder()
          .setAnchor(anchor3)
          .setShape(RoundedRectangle(1.5f*anchor3.height.toFloat(), 2*anchor3.width.toFloat(), 15f))
          .setOverlay(third, overlayHeight = overlayHeight.toFloat())
          .setOverlayAlignment(OverlayAlignment.TOP_LEFT)
          .setOnTargetListener(object : OnTargetListener {
            override fun onStarted() {
              currentToast?.cancel()
              currentToast = Toast.makeText(
                  requireContext(),
                  "third target is started",
                  Toast.LENGTH_SHORT
              )
              currentToast?.show()
            }

            override fun onEnded() {
              currentToast?.cancel()
              currentToast = Toast.makeText(
                  requireContext(),
                  "third target is ended",
                  Toast.LENGTH_SHORT
              )
              currentToast?.show()
            }
          })
          .build()

      targets.add(thirdTarget)

      // create spotlight
      val spotlight = Spotlight.Builder(requireActivity())
          .setTargets(targets)
          .setBackgroundColorRes(R.color.spotlightBackground)
          .setDuration(1000L)
          .setAnimation(DecelerateInterpolator(2f))
          .setOnSpotlightListener(object : OnSpotlightListener {
            override fun onStarted() {
              currentToast?.cancel()
              currentToast = Toast.makeText(
                  requireContext(),
                  "spotlight is started",
                  Toast.LENGTH_SHORT
              )
              currentToast?.show()
              startButton.isEnabled = false
            }

            override fun onEnded() {
              currentToast?.cancel()
              currentToast = Toast.makeText(
                  requireContext(),
                  "spotlight is ended",
                  Toast.LENGTH_SHORT
              )
              currentToast?.show()
              startButton.isEnabled = true
            }
          })
          .build()

      spotlight.start()

      val nextTarget = View.OnClickListener { spotlight.next() }

      val closeSpotlight = View.OnClickListener { spotlight.finish() }

      first.findViewById<View>(R.id.next_target).setOnClickListener(nextTarget)
      second.findViewById<View>(R.id.next_target).setOnClickListener(nextTarget)
      third.findViewById<View>(R.id.next_target).setOnClickListener(nextTarget)

      first.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
      second.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
      third.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
    }
  }
}