package com.takusemba.spotlight.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Rounded Rectangle shape of a target.
 * Height, width, and radius (rounded corners) is configurable.
 */
public class RoundedRectangle implements Shape {

  private float height;
  private float width;
  private float radius;

  public RoundedRectangle(float height, float width, float radius) {
    this.height = height;
    this.width = width;
    this.radius = radius;
  }

  @Override public void draw(Canvas canvas, PointF point, float value, Paint paint) {
    float halfWidth = width / 2 * value;
    float halfHeight = height / 2 * value;
    float left = point.x - halfWidth;
    float top = point.y - halfHeight;
    float right = point.x + halfWidth;
    float bottom = point.y + halfHeight;
    RectF rect = new RectF(left, top, right, bottom);
    canvas.drawRoundRect(rect, radius, radius, paint);
  }
}

