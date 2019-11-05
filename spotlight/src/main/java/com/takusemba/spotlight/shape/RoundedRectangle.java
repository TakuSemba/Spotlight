package com.takusemba.spotlight.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Rounded Rectangle shape of a target.
 * Height, width, and radius (rounded corners) is configurable.
 */
public class RoundedRectangle implements Shape {

  private Padding padding;
  private float radius;

  public RoundedRectangle() {
  }

  public RoundedRectangle(Padding padding) {
    this(padding, 0f);
  }

  public RoundedRectangle(float radius) {
    this(new Padding(0, 0), radius);
  }

  public RoundedRectangle(Padding padding, float radius) {
    this.padding = padding;
    this.radius = radius;
  }

  @Override public void draw(Canvas canvas, Rect rect, float value, Paint paint) {
    RectF rectF = new RectF(rect.left - padding.getX(), rect.top - padding.getY(),
        rect.right + padding.getX(), rect.bottom + padding.getY());
    float deltaX = (rectF.width() - (rectF.width() * value)) / 2;
    float deltaY = (rectF.height() - (rectF.height() * value)) / 2;
    RectF rectFScaled = new RectF(rectF.left + deltaX, rectF.top + deltaY, rectF.right - deltaX,
        rectF.bottom - deltaY);
    canvas.drawRoundRect(rectFScaled, radius, radius, paint);
  }
}

