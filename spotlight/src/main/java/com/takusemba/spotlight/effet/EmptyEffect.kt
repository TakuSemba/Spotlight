package com.takusemba.spotlight.effet

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF

/**
 * [Effect] that does not do anything.
 */
class EmptyEffect : Effect {

  override fun draw(canvas: Canvas, point: PointF, value: Float, paint: Paint) = Unit
}