package com.takusemba.spotlight.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

public interface Shape {

    /**
     * draw the Shape
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
