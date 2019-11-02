package com.takusemba.spotlight.target;

import android.animation.TimeInterpolator;
import android.app.Activity;
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

  private static final Rect DEFAULT_RECT = new Rect(0, 0, 0, 0);
  private static final long DEFAULT_DURATION = 1000L;
  private static final TimeInterpolator DEFAULT_ANIMATION = new DecelerateInterpolator(2f);
  private static final Shape DEFAULT_SHAPE = new Circle(0);
  private WeakReference<Activity> contextWeakReference;

  protected Rect rect = DEFAULT_RECT;
  protected RectSupplier deferredRectSupplier = null;
  protected Shape shape = DEFAULT_SHAPE;
  protected long duration = DEFAULT_DURATION;
  protected boolean autoStart = true;
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

  public T setRectSupplier(@NonNull RectSupplier deferredRectSupplier) {
    this.deferredRectSupplier = deferredRectSupplier;
    return self();
  }

  public T setRectSupplierFromView(@IdRes final int viewId) {
    this.deferredRectSupplier = new RectSupplier() {
      @Override public Rect get() {
        return getRelativeViewPosition(viewId, getContext());
      }
    };
    return self();
  }

  public T setRectSupplierFromView(@NonNull final View view) {
    this.deferredRectSupplier = new RectSupplier() {
      @Override public Rect get() {
        return getRelativeViewPosition(view, getContext());
      }
    };
    return self();
  }

  public T setRectFromView(@NonNull View view) {
    return setRect(getRelativeViewPosition(view, getContext()));
  }

  public T setRect(int left, int top, int right, int bottom) {
    setRect(new Rect(left, top, right, bottom));
    return self();
  }

  public T setRect(@NonNull Rect rect) {
    this.rect = rect;
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

  public T setAutoStart(boolean autoStart) {
    this.autoStart = autoStart;
    return self();
  }

  public T setAnimation(@NonNull TimeInterpolator animation) {
    this.animation = animation;
    return self();
  }

  public T setTargetListener(@NonNull final OnTargetStateChangedListener<S> listener) {
    this.listener = listener;
    return self();
  }

  private Rect getRelativeViewPosition(int viewId, Activity context) {
    View view = context.findViewById(viewId);
    return getRelativeViewPosition(view, context);
  }

  private Rect getRelativeViewPosition(View view, Activity context) {
    Rect offsetViewBounds = new Rect();
    view.getDrawingRect(offsetViewBounds);
    ViewGroup root = context.findViewById(android.R.id.content);
    root.offsetDescendantRectToMyCoords(view, offsetViewBounds);
    return offsetViewBounds;
  }
}
