package com.takusemba.spotlight.target;

import android.animation.TimeInterpolator;
import android.app.Activity;
import android.graphics.PointF;
import androidx.annotation.LayoutRes;
import android.view.View;

import com.takusemba.spotlight.OnTargetStateChangedListener;
import com.takusemba.spotlight.shape.Shape;

/**
 * Target
 *
 * @author takusemba
 * @since 26/06/2017
 **/
public class CustomTarget extends Target {

    private CustomTarget(Shape shape, PointF point, View overlay, long duration, TimeInterpolator animation, OnTargetStateChangedListener listener) {
        super(shape, point, overlay, duration, animation, listener);
    }

    public static class Builder extends AbstractTargetBuilder<Builder, CustomTarget> {

        @Override
        protected Builder self() {
            return this;
        }

        private View overlay;

        public Builder(Activity context) {
            super(context);
        }

        public Builder setOverlay(@LayoutRes int layoutId) {
            this.overlay = getContext().getLayoutInflater().inflate(layoutId, null);
            return this;
        }

        public Builder setOverlay(View overlay) {
            this.overlay = overlay;
            return this;
        }

        @Override
        public CustomTarget build() {
            return new CustomTarget(shape, point, overlay, duration, animation, listener);
        }
    }
}
