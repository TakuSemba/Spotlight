package com.takusemba.spotlight;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.View;

public class Circle extends Shape {
	private float mRadius;

	public Circle(View view) {
		setPoint(view);
		this.mRadius = (float) Math.sqrt(Math.pow(view.getWidth(), 2) + Math.pow(view.getHeight(), 2));
	}

	public Circle(PointF point, float radius) {
		this.mPoint = point;
		this.mRadius = radius;
	}

	@Override
	public void draw(Canvas canvas, float animValue, Paint paint) {
		canvas.drawCircle(mPoint.x, mPoint.y, animValue * mRadius, paint);
	}
}
