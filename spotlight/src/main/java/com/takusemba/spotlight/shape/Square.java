package com.takusemba.spotlight.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * @author：dz-hexiang
 * @email：472482006@qq.com
 */
public class Square implements Shape {

    private int width = 0;
    private int height = 0;
    private int radiusX = 0;
    private int radiusY = 0;

    public Square(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Square(int width, int height, int radiusX, int radiusY) {
        this.width = width;
        this.height = height;
        this.radiusX = radiusX;
        this.radiusY = radiusY;
    }

    public Square(@NonNull View view, int radiusX, int radiusY) {
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        measureView(view);
    }

    private void measureView(View view) {
        this.width = view.getWidth();
        this.height = view.getHeight();
        if (this.width <= 0 || this.height <= 0) {
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(w, h);
            if (this.height <= 0) {
                this.height = view.getMeasuredHeight();
            }
            if (this.width <= 0) {
                this.width = view.getMeasuredWidth();
            }
        }
    }

    public Square(@NonNull View view) {
        measureView(view);
    }


    @Override
    public void draw(Canvas canvas, PointF point, float value, Paint paint) {
        int halfWidth = (int) (value * (int) (value * width / 2));
        int halfHeight = (int) (value * (int) (value * height / 2));
        int left = (int) (point.x - halfWidth);
        int top = (int) (point.y - halfHeight);
        int right = (int) (point.x + halfWidth);
        int bottom = (int) (point.y + halfHeight);
        if (this.radiusX > 0 || this.radiusY > 0) {
            canvas.drawRoundRect(new RectF(left, top, right, bottom), value * this.radiusX, value * this.radiusY, paint);
        } else {
            canvas.drawRect(new Rect(left, top, right, bottom), paint);
        }

    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }
}