package com.takusemba.spotlight;

import android.graphics.PointF;
import android.view.View;

/**
 * Target
 *
 * @author takusemba
 * @since 26/06/2017
 **/
interface Target {

    /**
     * gets the point of this Target
     *
     * @return the point of this Target
     */
    PointF getPoint();

    /**
     * gets the view of this Target
     *
     * @return the view of this Target
     */
    View getView();

    /**
     * gets shape of this Target
     *
     * @return shape of this Target
     */
    Shape getShape();

    /**
     * gets the listener of this Target
     *
     * @return the listener of this Target
     */
    OnTargetStateChangedListener getListener();

    /**
     * default target
     */
    Target DEFAULT = new Target() {
        @Override
        public PointF getPoint() {
            return new PointF(0, 0);
        }

        @Override
        public View getView() {
            return null;
        }

        @Override
        public Shape getShape() {return new Circle(getPoint(), 100f);}

        @Override
        public OnTargetStateChangedListener getListener() {
            return null;
        }
    };
}