package com.takusemba.spotlightsample

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.takusemba.spotlight.OnTargetListener
import com.takusemba.spotlight.SpotlightCoordinator
import com.takusemba.spotlight.Target
import com.takusemba.spotlight.shape.Caption
import com.takusemba.spotlight.shape.CaptionType
import com.takusemba.spotlight.shape.DynamicShape
import java.lang.IllegalStateException

class MultiFragmentSampleFragment2 : Fragment(
    R.layout.multi_fragment_sample_fragment_2), MultiFragmentSampleActivity.SpotlightFragment {
  private var currentToast: Toast? = null

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    setupSpotlightTrigger()

    requireView().findViewById<View>(R.id.startMultiFragmentSampleFragment2).setOnClickListener {
      reinitSpotlight()
      (requireActivity() as SpotlightCoordinator).restartSpotlight()
    }
  }

  private fun setupSpotlightTrigger() {
    val viewTreeObserver = requireView().viewTreeObserver
    val onGlobalLayoutListener = object : ViewTreeObserver.OnGlobalLayoutListener {
      @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
      override fun onGlobalLayout() {
        try {
          viewTreeObserver.removeOnGlobalLayoutListener(this)
          reinitSpotlight()
        } catch (ex: IllegalStateException) {
        }
      }
    }

    viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
  }

  override fun reinitSpotlight() {
    val inflatingRoot = FrameLayout(requireContext())
    val overlay = layoutInflater.inflate(R.layout.layout_target, inflatingRoot)
    val view = requireView()

    val target1 = setupTarget1(view, inflatingRoot, overlay)

    val targets = listOf(
        target1,
    )

    (requireActivity() as SpotlightCoordinator).apply {
      setTargets(targets)
    }

    overlay.findViewById<View>(
        R.id.close_target).setOnClickListener { (requireActivity() as SpotlightCoordinator).next() }
    overlay.findViewById<View>(
        R.id.close_spotlight).setOnClickListener { (requireActivity() as SpotlightCoordinator).stop() }
  }

  private fun setupTarget1(
      view: View, inflatingRoot: FrameLayout, overlay: View
  ): Target {
    val view1 = view.findViewById<View>(R.id.scrollMultiFragmentSampleFragment21)
    val caption1 = layoutInflater.inflate(R.layout.caption_1,
        inflatingRoot, false).let {
      Caption(
          it,
          type = CaptionType.BEFORE,
      )
    }
    val caption2 = layoutInflater.inflate(R.layout.caption_2,
        inflatingRoot, false).let {
      Caption(
          it,
          type = CaptionType.AFTER,
      )
    }
    val target1 = Target.Builder()
        .setAnchor(view1)
        .setShape(DynamicShape(view1))
        .setCaptions(listOf(
            caption1,
            caption2,
        ))
        .setClickable(true)
        .setOverlay(overlay)
        .setOnTargetListener(object : OnTargetListener {
          override fun onStarted() {
            currentToast?.cancel()
            currentToast = Toast.makeText(
                requireContext(),
                "target21 is started",
                Toast.LENGTH_SHORT
            )
            currentToast?.show()
          }

          override fun onEnded() {
            try {
              currentToast?.cancel()
              currentToast = Toast.makeText(
                  requireContext(),
                  "target21 is ended",
                  Toast.LENGTH_SHORT
              )
              currentToast?.show()
            } catch (ex: IllegalStateException) {
            }
          }
        })
        .build()


    return target1
  }
}