package com.takusemba.spotlight.target;

import android.animation.TimeInterpolator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.takusemba.spotlight.OnTargetStateChangedListener;
import com.takusemba.spotlight.R;
import com.takusemba.spotlight.shape.Shape;

/**
 * SimpleTarget will set a simple overlay.
 * If you set your customized overlay, consider using {@link CustomTarget} instead.
 **/
public class SimpleTarget extends Target {

    private SimpleTarget(Shape shape, PointF point, View overlay, long duration, TimeInterpolator animation, OnTargetStateChangedListener listener) {
        super(shape, point, overlay, duration, animation, listener);
    }

    public static class Builder extends AbstractTargetBuilder<Builder, SimpleTarget> {

        @Override
        protected Builder self() {
            return this;
        }

        private static final int ABOVE_SPOTLIGHT = 0;
        private static final int BELOW_SPOTLIGHT = 1;

        private CharSequence title;
        private CharSequence description;

        public Builder(@NonNull Activity context) {
            super(context);
        }

        public Builder setTitle(CharSequence title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(CharSequence description) {
            this.description = description;
            return this;
        }

        @Override
        public SimpleTarget build() {
            View overlay = getContext().getLayoutInflater().inflate(R.layout.layout_spotlight, null);
            ((TextView) overlay.findViewById(R.id.title)).setText(title);
            ((TextView) overlay.findViewById(R.id.description)).setText(description);
            calculatePosition(point, shape, overlay);
            return new SimpleTarget(shape, point, overlay, duration, animation, listener);
        }

        private void calculatePosition(final PointF point, final Shape shape, View overlay) {
            float[] areas = new float[2];
            Point screenSize = new Point();
            ((WindowManager) overlay.getContext()
                    .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(screenSize);

            areas[ABOVE_SPOTLIGHT] = point.y / screenSize.y;
            areas[BELOW_SPOTLIGHT] = (screenSize.y - point.y) / screenSize.y;

            int largest;
            if (areas[ABOVE_SPOTLIGHT] > areas[BELOW_SPOTLIGHT]) {
                largest = ABOVE_SPOTLIGHT;
            } else {
                largest = BELOW_SPOTLIGHT;
            }

            final LinearLayout layout = overlay.findViewById(R.id.container);
            layout.setPadding(100, 0, 100, 0);
            switch (largest) {
                case ABOVE_SPOTLIGHT:
                    // use viewTreeObserver to use layout.getHeight()
                    layout.getViewTreeObserver()
                            .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                @Override
                                public void onGlobalLayout() {
                                    layout.setY(point.y - (shape.getHeight() / 2) - 100 - layout.getHeight());
                                }
                            });
                    break;
                case BELOW_SPOTLIGHT:
                    layout.setY((int) (point.y + (shape.getHeight() / 2) + 100));
                    break;
            }
        }
    }
}