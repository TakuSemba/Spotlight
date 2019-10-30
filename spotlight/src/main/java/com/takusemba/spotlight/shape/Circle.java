package com.takusemba.spotlight.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Circle shape of a target.
 * Radius is configurable.
 */
public class Circle implements Shape {

  private float padding;

  public Circle(int padding) {
    this.padding = padding;
  }

  @Override public void draw(Canvas canvas, Rect rect, float value, Paint paint) {
    canvas.drawCircle(rect.left + rect.width() / 2, rect.top + rect.height() / 2,
        value * (rect.width() + padding), paint);
  }
}
