package com.takusemba.spotlightsample.hiddentarget

import android.app.Activity
import android.graphics.PointF
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.takusemba.spotlight.OnSpotlightListener
import com.takusemba.spotlight.Spotlight
import com.takusemba.spotlight.Target
import com.takusemba.spotlight.shape.DynamicShape
import com.takusemba.spotlightsample.R

class HiddenTargetSampleActivity : AppCompatActivity(R.layout.activity_hidden_target_sample) {
  private lateinit var adapter: HiddenAdapter<HiddenItem>
  private lateinit var spotlight: Spotlight
  private lateinit var recyclerView: RecyclerView
  private lateinit var overlay: View

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    spotlight = initSpotlight(this)

    val inflatingRoot = FrameLayout(applicationContext)
    setupTargetOverlay(inflatingRoot)

    setupRecyclerView()
    setupStartSpotlightButton()
  }

  private fun setupTargetOverlay(inflatingRoot: FrameLayout) {
    overlay = layoutInflater.inflate(R.layout.layout_target, inflatingRoot).also {
      it.findViewById<View>(
          R.id.close_target).setOnClickListener { spotlight.next() }
      it.findViewById<View>(
          R.id.close_spotlight).setOnClickListener { spotlight.previous() }
    }
  }

  private fun setupStartSpotlightButton() {
    findViewById<View>(
        R.id.startHiddenTargetSampleActivity).setOnClickListener {

      spotlight.setTargets(listOf(
          makeTarget1(),
          makeTarget2(),
          makeTarget6()
      ))
      spotlight.start()
    }
  }

  private fun setupRecyclerView() {
    recyclerView = findViewById(R.id.recyclerViewHiddenTargetSampleActivity)

    adapter = HiddenAdapter()
    recyclerView.apply {
      adapter = this@HiddenTargetSampleActivity.adapter
      layoutManager = LinearLayoutManager(applicationContext)
    }

    (0..LAST_TARGET_POSITION)
        .map { HiddenItem("Hi ${it + 1}") }
        .let { adapter.submitList(it) }
  }

  private fun initSpotlight(activity: Activity): Spotlight {
    return Spotlight.Builder(activity)
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
        .build()
  }

  private fun makeTarget1(): Target {
    val view1 = recyclerView.findViewHolderForLayoutPosition(FIRST_TARGET_POSITION)?.itemView

    val builder1 =
        Target.Builder()
            .setOverlay(overlay)
            .setVisibilityChecker { genericVisibilityChecker(FIRST_TARGET_POSITION) }

    val target1 = if (view1 == null) {
      builder1.build()
    } else {
      builder1
          .setAnchor(view1)
          .setShape(DynamicShape(view1))
          .build()
    }

    val scrollListener1 = object : RecyclerView.OnScrollListener() {
      override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        genericScrollCallback(newState, recyclerView, target1, FIRST_TARGET_POSITION)
      }
    }

    target1
        .setActionToMakeVisible {
          genericActionToMakeVisible(scrollListener1, FIRST_TARGET_POSITION)
        }

    return target1
  }

  private fun makeTarget2(): Target {
    val view2 = recyclerView.findViewHolderForLayoutPosition(SECOND_TARGET_POSITION)?.itemView

    val builder2 =
        Target.Builder()
            .setOverlay(overlay)
            .setVisibilityChecker { genericVisibilityChecker(SECOND_TARGET_POSITION) }

    val target2 = if (view2 == null) {
      builder2.build()
    } else {
      builder2
          .setAnchor(view2)
          .setShape(DynamicShape(view2))
          .build()
    }

    val scrollListener2 = object : RecyclerView.OnScrollListener() {
      override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        genericScrollCallback(newState, recyclerView, target2, SECOND_TARGET_POSITION)
      }
    }

    target2
        .setAnchorChecker { genericAnchorChecker(target2) }
        .setAnchorRebuilder { genericAnchorRebuilder(target2) }
        .setActionToMakeVisible {
          genericActionToMakeVisible(scrollListener2, SECOND_TARGET_POSITION)
        }

    return target2
  }

  private fun makeTarget6(): Target {
    val target6 = Target.Builder()
        .setOverlay(overlay)
        .setVisibilityChecker { genericVisibilityChecker(LAST_TARGET_POSITION) }
        .build()

    val scrollListener6 = object : RecyclerView.OnScrollListener() {
      override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        genericScrollCallback(newState, recyclerView, target6, LAST_TARGET_POSITION)
      }
    }

    target6
        .setActionToMakeVisible {
          genericActionToMakeVisible(scrollListener6, LAST_TARGET_POSITION)
        }

    return target6
  }

  private fun genericAnchorChecker(target: Target): Boolean {
    return Rect().let {
      recyclerView.findViewHolderForLayoutPosition(
          SECOND_TARGET_POSITION)?.itemView?.getGlobalVisibleRect(it)
      it.contains(target.anchor.x.toInt(), target.anchor.y.toInt())
    }
  }

  private fun genericAnchorRebuilder(target: Target) {
    Rect().let { rect ->
      recyclerView.findViewHolderForLayoutPosition(
          SECOND_TARGET_POSITION)?.itemView?.let {
        it.getGlobalVisibleRect(rect)
        rect.contains(target.anchor.x.toInt(), target.anchor.y.toInt())
        target.anchor = PointF(rect.exactCenterX(), rect.exactCenterY())
        target.recalculateShape(it, DynamicShape(it))
        spotlight.current()
      }
    }
  }

  private fun RecyclerView.OnScrollListener.genericScrollCallback(
      newState: Int, recyclerView: RecyclerView,
      target: Target,
      position: Int
  ) {
    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
      val findViewHolderForAdapterPosition = recyclerView.findViewHolderForLayoutPosition(
          position)

      findViewHolderForAdapterPosition?.itemView?.let {
        recyclerView.removeOnScrollListener(this)
        target.recalculateShape(it, DynamicShape(it))
        spotlight.current()
      }
    }
  }

  private fun genericActionToMakeVisible(listener: RecyclerView.OnScrollListener, position: Int) {
    recyclerView.apply {
      addOnScrollListener(listener)
      smoothScrollToPosition(position)
    }
  }

  private fun genericVisibilityChecker(i: Int): Boolean {
    return (recyclerView.layoutManager as LinearLayoutManager).let {
      val firstItem = it.findFirstCompletelyVisibleItemPosition()
      val lastItem = it.findLastCompletelyVisibleItemPosition()

      i in firstItem..lastItem
    }
  }

  companion object {
    const val FIRST_TARGET_POSITION = 0
    const val SECOND_TARGET_POSITION = 1
    const val LAST_TARGET_POSITION = 5
  }
}
