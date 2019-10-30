package com.takusemba.spotlight.target;

import android.animation.TimeInterpolator;
import android.app.Activity;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import androidx.annotation.IdRes;
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
  protected PointSupplier deferredPointSupplier = null;
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

  public T setPointSupplier(@NonNull PointSupplier deferredPointSupplier) {
    this.deferredPointSupplier = deferredPointSupplier;
    return self();
  }

  public T setPointSupplierFromView(@IdRes final int viewId) {
    this.deferredPointSupplier = new PointSupplier() {
      @Override public PointF get() {
        return getRelativeViewPosition(viewId, getContext());
      }
    };
    return self();
  }

  public T setPointSupplierFromView(@NonNull final View view) {
    this.deferredPointSupplier = new PointSupplier() {
      @Override public PointF get() {
        return getRelativeViewPosition(view, getContext());
      }
    };
    return self();
  }

  public T setPoint(@NonNull View view) {
    return setPoint(getRelativeViewPosition(view, getContext()));
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

  private PointF getRelativeViewPosition(int viewId, Activity context) {
    View view = context.findViewById(viewId);
    return getRelativeViewPosition(view, context);
  }

  private PointF getRelativeViewPosition(View view, Activity context) {
    Rect offsetViewBounds = new Rect();
    view.getDrawingRect(offsetViewBounds);
    ViewGroup root = context.findViewById(android.R.id.content);
    root.offsetDescendantRectToMyCoords(view, offsetViewBounds);
    int x = offsetViewBounds.left + view.getWidth() / 2;
    int y = offsetViewBounds.top + view.getHeight() / 2;
    return new PointF(x, y);
  }
}
