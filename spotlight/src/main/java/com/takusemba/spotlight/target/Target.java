package com.takusemba.spotlight.target;

import android.animation.TimeInterpolator;
import android.graphics.Rect;
import android.view.View;
import com.takusemba.spotlight.OnTargetStateChangedListener;
import com.takusemba.spotlight.shape.Circle;
import com.takusemba.spotlight.shape.Shape;

/**
 * Target is used to light up a certain area.
 * The shape of the Target is customizable using {@link Shape} class.
 * If you want to show circle shape of a Target, set {@link Circle} class.
 **/
public abstract class Target {

  private Shape shape;
  private Rect rect;
  private RectSupplier deferredRectSupplier;
  private View overlay;
  private long duration;
  private TimeInterpolator animation;
  private OnTargetStateChangedListener listener;

  public Target(Shape shape, Rect rect, RectSupplier deferredRectSupplier, View overlay,
      long duration, TimeInterpolator animation, OnTargetStateChangedListener listener) {
    this.shape = shape;
    this.rect = rect;
    this.deferredRectSupplier = deferredRectSupplier;
    this.overlay = overlay;
    this.duration = duration;
    this.animation = animation;
    this.listener = listener;
  }

  public void setRect() {
    if (deferredRectSupplier != null) {
      rect = deferredRectSupplier.get();
    }
  }

  /**
   * gets the rect of this Target
   *
   * @return the rect of this Target
   */
  public Rect getRect() {
    return rect;
  }

  /**
   * gets the view of this Target
   *
   * @return the view of this Target
   */
  public View getOverlay() {
    return overlay;
  }

  /**
   * gets shape of this Target
   *
   * @return shape of this Target
   */
  public Shape getShape() {
    return shape;
  }

  /**
   * gets duration of this Target
   *
   * @return duration of this Target
   */
  public long getDuration() {
    return duration;
  }

  /**
   * gets animation of this Target
   *
   * @return animation of this Target
   */
  public TimeInterpolator getAnimation() {
    return animation;
  }

  /**
   * gets the listener of this Target
   *
   * @return the listener of this Target
   */
  public OnTargetStateChangedListener getListener() {
    return listener;
  }
}