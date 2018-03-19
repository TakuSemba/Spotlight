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
    private View view;
    private OnTargetStateChangedListener stateListener;
    private OnTargetActionListener actionListener;
    private Shape shape;

    /**
     * Constructor
     */
    private CustomTarget(Shape shape, View view, OnTargetStateChangedListener listener) {
        this.shape = shape;
        this.view = view;
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
        return shape.getPoint();
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public Shape getForegroundShape() {
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
            return new CustomTarget(shape, view, listener);
        }
    }
}
