package com.takusemba.spotlightsample

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.takusemba.spotlight.OnTargetListener
import com.takusemba.spotlight.SpotlightCoordinator
import com.takusemba.spotlight.Target
import com.takusemba.spotlight.shape.Caption
import com.takusemba.spotlight.shape.CaptionType
import com.takusemba.spotlight.shape.DynamicShape
import com.takusemba.spotlight.shape.Margins

class MultiFragmentSampleFragment1 : Fragment(R.layout.multi_fragment_sample_fragment_1), MultiFragmentSampleActivity.SpotlightFragment {
  private var currentToast: Toast? = null

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    requireView().findViewById<View>(R.id.startMultiFragmentSampleFragment1).setOnClickListener {
      reinitSpotlight()
      (requireActivity() as SpotlightCoordinator).restartSpotlight()
    }

    requireView().findViewById<View>(
        R.id.textViewMultiFragmentSampleFragment11).setOnClickListener {
      showFragment2()
    }
  }

  private fun showFragment2() {
    requireActivity().supportFragmentManager.beginTransaction()
        .add(R.id.rootViewActivityMultiFragmentSample, MultiFragmentSampleFragment2())
        .addToBackStack(null)
        .commit()
  }

  override fun reinitSpotlight() {
    val inflatingRoot = FrameLayout(requireContext())
    val overlay = layoutInflater.inflate(R.layout.layout_target, inflatingRoot)
    val view = requireView()

    val target1 = setupTarget1(view, inflatingRoot, overlay)
    val target2 = setupTarget2(view, inflatingRoot, overlay)

    val targets = listOf(
        target1,
        target2
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
    val view1 = view.findViewById<View>(R.id.scrollMultiFragmentSampleFragment11)
    val caption1 = layoutInflater.inflate(R.layout.caption_1,
        inflatingRoot, false).let {
      Caption(
          it,
          type = CaptionType.BEFORE,
          margins = Margins(parentBefore = 10, parentAfter = 200),
      )
    }
    val caption2 = layoutInflater.inflate(R.layout.caption_2,
        inflatingRoot, false).let {
      Caption(
          it,
          type = CaptionType.AFTER,
          margins = Margins(parentBefore = 200, parentAfter = 10),
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
                "target1 is started",
                Toast.LENGTH_SHORT
            )
            currentToast?.show()
          }

          override fun onEnded() {
            currentToast?.cancel()
            currentToast = Toast.makeText(
                requireContext(),
                "target1 is ended",
                Toast.LENGTH_SHORT
            )
            currentToast?.show()
          }
        })
        .build()


    return target1
  }

  /**
   * Target 2 is the same as target 1, except it is not clickable. Since it is a scroll view, you
   * won't be able to scroll.
   */
  private fun setupTarget2(
      view: View, inflatingRoot: FrameLayout, overlay: View
  ): Target {
    val view2 = view.findViewById<View>(R.id.scrollMultiFragmentSampleFragment12)
    val caption1 = layoutInflater.inflate(R.layout.caption_1,
        inflatingRoot, false).let {
      Caption(
          it,
          type = CaptionType.BEFORE,
          margins = Margins(parentBefore = 200, parentAfter = 200),
      )
    }
    val caption2 = layoutInflater.inflate(R.layout.caption_2,
        inflatingRoot, false).let {
      Caption(
          it,
          type = CaptionType.AFTER,
          margins = Margins(parentBefore = 200, parentAfter = 200),
      )
    }
    val target2 = Target.Builder()
        .setAnchor(view2)
        .setShape(DynamicShape(view2))
        .setCaptions(listOf(
            caption1,
            caption2,
        ))
        .setClickable(false)
        .setOverlay(overlay)
        .setOnTargetListener(object : OnTargetListener {
          override fun onStarted() {
            currentToast?.cancel()
            currentToast = Toast.makeText(
                requireContext(),
                "target2 is started",
                Toast.LENGTH_SHORT
            )
            currentToast?.show()
          }

          override fun onEnded() {
            currentToast?.cancel()
            currentToast = Toast.makeText(
                requireContext(),
                "target2 is ended",
                Toast.LENGTH_SHORT
            )
            currentToast?.show()
          }
        })
        .build()

    return target2
  }
}