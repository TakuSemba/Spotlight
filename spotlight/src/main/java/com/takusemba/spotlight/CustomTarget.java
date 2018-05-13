package com.takusemba.spotlight;

import android.app.Activity;
import android.graphics.PointF;
import android.support.annotation.LayoutRes;
import android.view.View;

import com.takusemba.spotlight.shapes.Shape;

/**
 * Target
 *
 * @author takusemba
 * @since 26/06/2017
 **/
public class CustomTarget implements Target {
    private View overlay;
    private OnTargetStateChangedListener stateListener;
    private Shape shape;

    /**
     * Constructor
     */
    private CustomTarget(Shape shape, View overlay, OnTargetStateChangedListener listener) {
        this.shape = shape;
        this.overlay = overlay;
        this.stateListener = listener;
    }

    @Override
    public PointF getPoint() {
        return shape.getPoint();
    }

    @Override
    public View getOverlay() {
        return overlay;
    }

    @Override
    public Shape getShape() {
        return shape;
    }

    @Override
    public OnTargetStateChangedListener getListener() {
        return stateListener;
    }

    /**
     * Builder class which makes it easier to create {@link CustomTarget}
     */
    public static class Builder extends AbstractBuilder<Builder, CustomTarget> {

        @Override
        protected Builder self() {
            return this;
        }

        private View overlay;

        /**
         * Constructor
         */
        public Builder(Activity context) {
            super(context);
        }

        /**
         * Set the custom view shown on Spotlight
         *
         * @param layoutId layout id shown on Spotlight
         * @return This Builder
         */
        public Builder setOverlay(@LayoutRes int layoutId) {
            if (getContext() == null) {
                throw new RuntimeException("context is null");
            }
            this.overlay = getContext().getLayoutInflater().inflate(layoutId, null);
            return this;
        }

        /**
         * Set the custom view shown on Spotlight
         *
         * @param overlay the overlay view shown on Spotlight
         * @return This Builder
         */
        public Builder setOverlay(View overlay) {
            this.overlay = overlay;
            return this;
        }

        /**
         * Create the {@link CustomTarget}
         *
         * @return the created CustomTarget
         */
        @Override
        public CustomTarget build() {
            return new CustomTarget(shape, overlay, listener);
        }
    }
}
