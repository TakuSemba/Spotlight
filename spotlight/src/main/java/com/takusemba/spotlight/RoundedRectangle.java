package com.takusemba.spotlight;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.RequiresApi;
import android.view.View;

@RequiresApi(21)
public class RoundedRectangle extends Shape {
	private float mHalfWidth;
	private float mHalfHeight;
	private float mRadius;

	private float mAnimWidth;
	private float mAnimHeight;

	public RoundedRectangle(View view, float radius) {
		setPoint(view);
		this.mHalfHeight = view.getHeight() / 2;
		this.mHalfWidth = view.getWidth() / 2;
		this.mRadius = radius;
	}

	public RoundedRectangle(PointF pointF, float width, float height, float radius) {
		this.mPoint = pointF;
		this.mHalfWidth = width / 2;
		this.mHalfHeight = height / 2;
		this.mRadius = radius;
	}

	@Override
	void draw(Canvas canvas, float animValue, Paint paint) {
		mAnimWidth = mHalfWidth * animValue;
		mAnimHeight = mHalfHeight * animValue;

		canvas.drawRoundRect(mPoint.x - mAnimWidth,
				mPoint.y - mAnimHeight,
				mPoint.x + mAnimWidth,
				mPoint.y + mAnimHeight, mRadius,
				mRadius,
				paint);
	}
}
