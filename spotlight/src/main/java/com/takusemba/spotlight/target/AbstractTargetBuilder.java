package com.takusemba.spotlight.target;

import android.animation.TimeInterpolator;
import android.app.Activity;
import android.graphics.PointF;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import androidx.annotation.NonNull;
import com.takusemba.spotlight.OnTargetStateChangedListener;
import com.takusemba.spotlight.shape.Circle;
import com.takusemba.spotlight.shape.Shape;
import java.lang.ref.WeakReference;

/**
 * Abstraction layer of Target Builder containing all of the required params.
 * When creating a target from a builder, this class should be used.
 * T is a actual Builder class, S a actual Target class created from the builder
 **/
public abstract class AbstractTargetBuilder<T extends AbstractTargetBuilder<T, S>, S extends Target> {

  private static final PointF DEFAULT_POINT = new PointF(0, 0);
  private static final long DEFAULT_DURATION = 1000L;
  private static final TimeInterpolator DEFAULT_ANIMATION = new DecelerateInterpolator(2f);
  private static final Shape DEFAULT_SHAPE = new Circle(100);
  private WeakReference<Activity> contextWeakReference;

  protected PointF point = DEFAULT_POINT;
  protected Shape shape = DEFAULT_SHAPE;
  protected long duration = DEFAULT_DURATION;
  protected TimeInterpolator animation = DEFAULT_ANIMATION;
  protected OnTargetStateChangedListener listener = null;

  protected abstract T self();

  protected abstract S build();

  protected Activity getContext() {
    return contextWeakReference.get();
  }

  public AbstractTargetBuilder(@NonNull Activity context) {
    contextWeakReference = new WeakReference<>(context);
  }

  public T setPoint(@NonNull View view) {
    int[] location = new int[2];
    view.getLocationInWindow(location);
    int x = location[0] + view.getWidth() / 2;
    int y = location[1] + view.getHeight() / 2;
    return setPoint(x, y);
  }

  public T setPoint(float x, float y) {
    setPoint(new PointF(x, y));
    return self();
  }

  public T setPoint(@NonNull PointF point) {
    this.point = point;
    return self();
  }

  public T setShape(Shape shape) {
    if (shape == null) {
      throw new IllegalArgumentException("Shape cannot be null");
    }
    this.shape = shape;
    return self();
  }

  public T setDuration(@NonNull long duration) {
    this.duration = duration;
    return self();
  }

  public T setAnimation(@NonNull TimeInterpolator animation) {
    this.animation = animation;
    return self();
  }

  public T setOnSpotlightStartedListener(@NonNull final OnTargetStateChangedListener<S> listener) {
    this.listener = listener;
    return self();
  }
}
