package com.takusemba.spotlight;

import android.app.Activity;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Created by takusemba on 2017/06/28.
 */

abstract class AbstractBuilder<T extends AbstractBuilder<T, S>, S extends Target> {

    private WeakReference<Activity> contextWeakReference;
    protected OnTargetStateChangedListener listener;
    protected float startX = 0f;
    protected float startY = 0f;
    protected float radius = 100f;

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
    protected Activity getContext() {
        return contextWeakReference.get();
    }

    /**
     * Constructor
     */
    protected AbstractBuilder(@NonNull Activity context) {
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
        this.startX = x;
        this.startY = y;
        return self();
    }

    /**
     * Sets the initial position of target
     *
     * @param point starting position where spotlight reveals
     * @return This Builder
     */
    public T setPoint(@NonNull PointF point) {
        return setPoint(point.x, point.y);
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
     * Sets the radius of target
     *
     * @param radius radius of target
     * @return This Builder
     */
    public T setRadius(float radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("radius must be greater than 0");
        }
        this.radius = radius;
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
