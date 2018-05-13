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
    private View overlay;
    private OnTargetStateChangedListener stateListener;
    private OnTargetActionListener actionListener;

    /**
     * Constructor
     */
    private CustomTarget(PointF point, float radius, View overlay,
                         OnTargetStateChangedListener listener) {
        this.point = point;
        this.radius = radius;
        this.overlay = overlay;
        this.stateListener = listener;
    }

    interface OnTargetActionListener {
        void closeRequested();
    }

    void setOnTargetActionListener(OnTargetActionListener listener) {
        this.actionListener = listener;
    }

    public void closeTarget() {
        actionListener.closeRequested();
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
    public View getOverlay() {
        return overlay;
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
            PointF point = new PointF(startX, startY);
            return new CustomTarget(point, radius, overlay, listener);
        }
    }
}
