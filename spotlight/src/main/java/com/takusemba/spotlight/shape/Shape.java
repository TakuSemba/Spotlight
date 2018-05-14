package com.takusemba.spotlight.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

public interface Shape {

    /**
     * draw the Shape
     *
     * @param value the animated value from 0 to 1
     */
    void draw(Canvas canvas, PointF point, float value, Paint paint);

    /**
     * get the height of the Shape
     */
    int getHeight();

    /**
     * get the with of the Shape
     */
    int getWidth();
}
