package com.takusemba.spotlight.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

/**
 * Circle shape of a target.
 * Radius is configurable.
 */
public class Circle implements Shape {

  private float radius;

  public Circle(float radius) {
    this.radius = radius;
  }

  @Override public void draw(Canvas canvas, PointF point, float value, Paint paint) {
    canvas.drawCircle(point.x, point.y, value * radius, paint);
  }

  @Override public boolean contains(PointF point, float x, float y) {
    double dist = Math.hypot(point.x - x, point.y - y);
    return dist < radius;
  }
}
