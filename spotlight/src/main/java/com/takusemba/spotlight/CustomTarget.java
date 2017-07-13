package com.takusemba.spotlight;

import android.app.Activity;
import android.graphics.PointF;
import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * Target
 *
 * @author takusemba
 * @since 26/06/2017
 **/
public class CustomTarget implements Target {

    private PointF point;
    private float radius;
    private View view;
    private OnTargetStateChangedListener listener;

    /**
     * Constructor
     */
    private CustomTarget(PointF point, float radius, View view, OnTargetStateChangedListener listener) {
        this.point = point;
        this.radius = radius;
        this.view = view;
        this.listener = listener;
    }

    @Override
    public PointF getPoint() {
        return point;
    }

    @Override
    public float getRadius() {
        return radius;
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public OnTargetStateChangedListener getListener() {
        return listener;
    }

    /**
     * Builder class which makes it easier to create {@link CustomTarget}
     */
    public static class Builder extends AbstractBuilder<Builder, CustomTarget> {

        @Override
        protected Builder self() {
            return this;
        }

        private View view;

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
        public Builder setView(@LayoutRes int layoutId) {
            if (getContext() == null) {
                throw new RuntimeException("context is null");
            }
            this.view = getContext().getLayoutInflater().inflate(layoutId, null);
            return this;
        }

        /**
         * Set the custom view shown on Spotlight
         *
         * @param view view shown on Spotlight
         * @return This Builder
         */
        public Builder setView(View view) {
            this.view = view;
            return this;
        }

        /**
         * Create the {@link CustomTarget}
         *
         * @return the created CustomTarget
         */
        @Override
        public CustomTarget build() {
            PointF point = new PointF(startX, startY);
            return new CustomTarget(point, radius, view, listener);
        }
    }
}
