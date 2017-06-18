package com.takusemba.spotlightapp

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.*
import com.takusemba.spotlightapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val SCALE_INITIAL = 1f
    private val SCALE_STIFFNESS = SpringForce.STIFFNESS_VERY_LOW
    private val SCALE_DAMPING_RATIO = SpringForce.DAMPING_RATIO_NO_BOUNCY

    private val TRANSLATE_STIFFNESS = SpringForce.STIFFNESS_VERY_LOW
    private val TRANSLATE_DAMPING_RATIO = SpringForce.DAMPING_RATIO_NO_BOUNCY

    lateinit var scaleXAnimation: SpringAnimation
    lateinit var scaleYAnimation: SpringAnimation
    lateinit var translateXAnimation: SpringAnimation
    lateinit var translateYAnimation: SpringAnimation

    lateinit var scaleGestureDetector: ScaleGestureDetector
    lateinit var simpleGestureDetector: GestureDetector

    lateinit var bdg: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bdg = DataBindingUtil.setContentView(this, R.layout.activity_main)

        scaleXAnimation = createSpringAnimation(
                bdg.target, SpringAnimation.SCALE_X,
                SCALE_INITIAL, SCALE_STIFFNESS, SCALE_DAMPING_RATIO)
        scaleYAnimation = createSpringAnimation(
                bdg.target, SpringAnimation.SCALE_Y,
                SCALE_INITIAL, SCALE_STIFFNESS, SCALE_DAMPING_RATIO)

        setupListeners()

        // create X and Y animations for view's initial position once it's known
        bdg.target.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                translateXAnimation = createSpringAnimation(
                        bdg.target, SpringAnimation.X, bdg.target.x, TRANSLATE_STIFFNESS, TRANSLATE_DAMPING_RATIO)
                translateYAnimation = createSpringAnimation(
                        bdg.target, SpringAnimation.Y, bdg.target.y, TRANSLATE_STIFFNESS, TRANSLATE_DAMPING_RATIO)
                bdg.target.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

        var dX = 0f
        var dY = 0f
        bdg.target.setOnTouchListener { view, event ->
            scaleGestureDetector.onTouchEvent(event)
            simpleGestureDetector.onTouchEvent(event)

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // capture the difference between view's top left corner and touch point
                    dX = view.x - event.rawX
                    dY = view.y - event.rawY

                    // cancel animations so we can grab the view during previous animation
                    translateXAnimation.cancel()
                    translateYAnimation.cancel()
                }
                MotionEvent.ACTION_MOVE -> {
                    //  a different approach would be to change the view's LayoutParams.
//                    bdg.target.animate()
//                            .x(event.rawX + dX)
//                            .y(event.rawY + dY)
//                            .setDuration(0)
//                            .start()
                }
                MotionEvent.ACTION_UP -> {
                    translateXAnimation.start()
                    translateYAnimation.start()
                }
            }

            true
        }
    }

    fun createSpringAnimation(view: View,
                              property: DynamicAnimation.ViewProperty,
                              finalPosition: Float,
                              stiffness: Float,
                              dampingRatio: Float): SpringAnimation {
        val animation = SpringAnimation(view, property)
        val spring = SpringForce(finalPosition)
        spring.stiffness = stiffness
        spring.dampingRatio = dampingRatio
        animation.spring = spring
        return animation
    }

    private fun setupListeners() {
        var scaleFactor = 1f
        simpleGestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
//                if (Math.abs(e2.rawX - e1.rawX) > 10 || Math.abs(e2.rawY - e1.rawY) > 10) {
//                    return super.onScroll(e1, e2, distanceX, distanceY)
//                }
//                Log.d("mudebug", "x: " + (e2.rawX - e1.rawX))
//                Log.d("mudebug", "e1.rawX: " + e1.rawX)
//                Log.d("mudebug", "e2.rawX: " + e2.rawX)
//
//                Log.d("mudebug", "y: " + (e2.rawX - e1.rawX))

//                bdg.target.animate()
//                        .translationX(e2.rawX - e1.rawX)
//                        .translationY(e2.rawY - e1.rawY)
//                        .setDuration(0)
//                        .start()
                return super.onScroll(e1, e2, distanceX, distanceY)
            }

        })
        scaleGestureDetector = ScaleGestureDetector(this,
                object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
                    override fun onScale(detector: ScaleGestureDetector): Boolean {
                        scaleFactor *= detector.scaleFactor
                        bdg.target.scaleX *= scaleFactor
                        bdg.target.scaleY *= scaleFactor

                        Log.d("mydebug", "current X: " + detector.currentSpanX)
                        Log.d("mydebug", "current Y: " + detector.currentSpanY)


                        return true
                    }

                    override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
                        return super.onScaleBegin(detector)
                    }
                })
    }
}
