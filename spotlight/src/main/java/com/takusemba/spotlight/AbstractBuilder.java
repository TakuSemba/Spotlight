package com.takusemba.spotlight;

import android.app.Activity;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.view.View;
import com.takusemba.spotlight.shapes.Circle;
import com.takusemba.spotlight.shapes.Shape;

import java.lang.ref.WeakReference;

/**
 * Position Target
 *
 * @author takusemba
 * @since 26/06/2017
 **/
abstract class AbstractBuilder<T extends AbstractBuilder<T, S>, S extends Target> {

    private WeakReference<Activity> contextWeakReference;

    OnTargetStateChangedListener listener;
    Shape shape = new Circle(new PointF(0f, 0f), 100f);

    /**
     * return the builder itself
     */
    protected abstract T self();

    /**
     * return the built {@link Target}
     */
    protected abstract S build();

    /**
     * Return context weak reference
     *
     * @return the activity
     */
    Activity getContext() {
        return contextWeakReference.get();
    }

    /**
     * Constructor
     */
    AbstractBuilder(@NonNull Activity context) {
        contextWeakReference = new WeakReference<>(context);
    }

    /**
     * Sets the initial position of target
     *
     * @param y starting position of y where spotlight reveals
     * @param x starting position of x where spotlight reveals
     * @return This Builder
     */
    public T setPoint(float x, float y) {
        return setPoint(new PointF(x, y));
    }

    /**
     * Sets the initial position of target
     *
     * @param point starting position where spotlight reveals
     * @return This Builder
     */
    public T setPoint(@NonNull PointF point) {
        shape.setPoint(point);
        return self();
    }

    /**
     * Sets the initial position of target
     * Make sure the view already has a fixed position
     *
     * @param view starting position where spotlight reveals
     * @return This Builder
     */
    public T setPoint(@NonNull View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        int x = location[0] + view.getWidth() / 2;
        int y = location[1] + view.getHeight() / 2;
        return setPoint(x, y);
    }

    /**
     * Sets shape of the target
     *
     * @param shape shape of the target
     * @return This Builder
     */
    public T setShape(Shape shape) {
        if (shape == null)
            throw new IllegalArgumentException("Shape cannot be null");
        this.shape = shape;
        return self();
    }

    /**
     * Sets Target state changed Listener to target
     *
     * @param listener OnTargetStateChangedListener of target
     * @return This Builder
     */
    public T setOnSpotlightStartedListener(@NonNull final OnTargetStateChangedListener<S> listener) {
        this.listener = listener;
        return self();
    }
}
