package com.takusemba.spotlight.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.View;

public abstract class Shape {
	protected PointF point = new PointF(0f, 0f);

	public abstract void draw(Canvas canvas, float animValue, Paint paint);

	protected void setPoint(View view) {
		int[] location = new int[2];
		view.getLocationInWindow(location);
		int x = location[0] + view.getWidth() / 2;
		int y = location[1] + view.getHeight() / 2;
		setPoint(x, y);
	}

	public void setPoint(PointF point) {
		this.point = point;
	}

	public void setPoint(float x, float y) {
		this.point = new PointF(x, y);
	}

	public PointF getPoint() {
		return point;
	}
}
