package com.takusemba.spotlight;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.takusemba.spotlight.shapes.Shape;

/**
 * Position Target
 *
 * @author takusemba
 * @since 26/06/2017
 **/
public class SimpleTarget implements Target {
    private View overlay;
    private OnTargetStateChangedListener listener;
    private Shape shape;

    /**
     * Constructor
     */
    private SimpleTarget(Shape shape, View overlay, OnTargetStateChangedListener listener) {
        this.overlay = overlay;
        this.listener = listener;
        this.shape = shape;
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
        return listener;
    }

    /**
     * Builder class which makes it easier to create {@link SimpleTarget}
     */
    public static class Builder extends AbstractBuilder<Builder, SimpleTarget> {

        @Override
        protected Builder self() {
            return this;
        }

        private static final int ABOVE_SPOTLIGHT = 0;
        private static final int BELOW_SPOTLIGHT = 1;

        private CharSequence title;
        private CharSequence description;

        /**
         * Constructor
         */
        public Builder(@NonNull Activity context) {
            super(context);
        }

        /**
         * Set the title text shown on Spotlight
         *
         * @param title title shown on Spotlight
         * @return This Builder
         */
        public Builder setTitle(CharSequence title) {
            this.title = title;
            return this;
        }

        /**
         * Set the description text shown on Spotlight
         *
         * @param description title shown on Spotlight
         * @return This Builder
         */
        public Builder setDescription(CharSequence description) {
            this.description = description;
            return this;
        }

        /**
         * Create the {@link SimpleTarget}
         *
         * @return the created SimpleTarget
         */
        @Override
        public SimpleTarget build() {
            if (getContext() == null) {
                throw new RuntimeException("context is null");
            }
            View view = getContext().getLayoutInflater().inflate(R.layout.layout_spotlight, null);
            ((TextView) view.findViewById(R.id.title)).setText(title);
            ((TextView) view.findViewById(R.id.description)).setText(description);
            calculatePosition(shape.getPoint(), view.getPivotY(), view);
            return new SimpleTarget(shape, view, listener);
        }

        /**
         * calculate the position of title and description based off of where the spotlight reveals
         */
        private void calculatePosition(final PointF point, final float radius, View spotlightView) {
            float[] areas = new float[2];
            Point screenSize = new Point();
            ((WindowManager) spotlightView.getContext()
                    .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(screenSize);

            areas[ABOVE_SPOTLIGHT] = point.y / screenSize.y;
            areas[BELOW_SPOTLIGHT] = (screenSize.y - point.y) / screenSize.y;

            int largest;
            if (areas[ABOVE_SPOTLIGHT] > areas[BELOW_SPOTLIGHT]) {
                largest = ABOVE_SPOTLIGHT;
            } else {
                largest = BELOW_SPOTLIGHT;
            }

            final LinearLayout layout = spotlightView.findViewById(R.id.container);
            layout.setPadding(100, 0, 100, 0);
            switch (largest) {
                case ABOVE_SPOTLIGHT:
                    spotlightView.getViewTreeObserver()
                            .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                @Override
                                public void onGlobalLayout() {
                                    layout.setY(point.y - radius - 100 - layout.getHeight());
                                }
                            });
                    break;
                case BELOW_SPOTLIGHT:
                    layout.setY((int) (point.y + radius + 100));
                    break;
            }
        }
    }
}