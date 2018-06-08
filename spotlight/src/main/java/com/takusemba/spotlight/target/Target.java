package com.takusemba.spotlight.target;

import android.animation.TimeInterpolator;
import android.graphics.PointF;
import android.view.View;

import com.takusemba.spotlight.OnTargetStateChangedListener;
import com.takusemba.spotlight.shape.Shape;

/**
 * Target
 *
 * @author takusemba
 * @since 26/06/2017
 **/
public abstract class Target {

    private Shape shape;
    private PointF point;
    private View overlay;
    private long duration;
    private TimeInterpolator animation;
    private OnTargetStateChangedListener listener;

    public Target(Shape shape, PointF point, View overlay, long duration, TimeInterpolator animation, OnTargetStateChangedListener listener) {
        this.shape = shape;
        this.point = point;
        this.overlay = overlay;
        this.duration = duration;
        this.animation = animation;
        this.listener = listener;
    }

    /**
     * gets the point of this Target
     *
     * @return the point of this Target
     */
    public PointF getPoint() {
        return point;
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