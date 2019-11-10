package com.takusemba.spotlight.target

import android.animation.TimeInterpolator
import android.content.Context
import android.graphics.PointF
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.takusemba.spotlight.shape.Circle
import com.takusemba.spotlight.shape.Shape
import java.util.concurrent.TimeUnit

/**
 * Abstraction layer of Target Builder containing all of the required params. When creating a target
 * from a builder, this class should be used. T is a actual Builder class, S a actual Target class
 * created from the builder
 */
abstract class AbstractTargetBuilder<T : AbstractTargetBuilder<T, S>, S : Target>(
    protected val context: Context
) {

  protected var point: PointF = DEFAULT_POINT
  protected var shape: Shape = DEFAULT_SHAPE
  protected var duration: Long = DEFAULT_DURATION
  protected var animation: TimeInterpolator = DEFAULT_ANIMATION

  protected abstract fun self(): T

  protected abstract fun build(): S

  fun setPoint(view: View): T {
    val location = IntArray(2)
    view.getLocationInWindow(location)
    val x = location[0] + view.width / 2
    val y = location[1] + view.height / 2
    return setPoint(x.toFloat(), y.toFloat())
  }

  fun setPoint(x: Float, y: Float): T {
    setPoint(PointF(x, y))
    return self()
  }

  fun setPoint(point: PointF): T {
    this.point = point
    return self()
  }

  fun setShape(shape: Shape): T {
    this.shape = shape
    return self()
  }

  fun setDuration(duration: Long): T {
    this.duration = duration
    return self()
  }

  fun setAnimation(animation: TimeInterpolator): T {
    this.animation = animation
    return self()
  }

  companion object {

    private val DEFAULT_POINT = PointF(0f, 0f)
    private val DEFAULT_DURATION = TimeUnit.SECONDS.toMillis(1)
    private val DEFAULT_ANIMATION = DecelerateInterpolator(2f)
    private val DEFAULT_SHAPE = Circle(100f)
  }
}
