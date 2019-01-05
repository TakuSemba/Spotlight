package com.takusemba.spotlight;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.takusemba.spotlight.target.Target;

/**
 * Spotlight View which holds a current {@link Target} and show it properly.
 **/
@SuppressLint("ViewConstructor")
class SpotlightView extends FrameLayout {

  private final Paint paint = new Paint();
  private final Paint spotPaint = new Paint();
  private ValueAnimator animator;
  @ColorRes private int overlayColor;
  private Target currentTarget;

  public SpotlightView(@NonNull Context context, @ColorRes int overlayColor,
      final OnSpotlightListener listener) {
    super(context, null);
    this.overlayColor = overlayColor;
    bringToFront();
    setWillNotDraw(false);
    setLayerType(View.LAYER_TYPE_HARDWARE, null);
    spotPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT));
    setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        if (animator != null && !animator.isRunning() && (float) animator.getAnimatedValue() > 0) {
          if (listener != null) listener.onSpotlightViewClicked();
        }
      }
    });
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    paint.setColor(ContextCompat.getColor(getContext(), overlayColor));
    canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
    if (animator != null && currentTarget != null) {
      currentTarget.getShape()
          .draw(canvas, currentTarget.getPoint(), (float) animator.getAnimatedValue(), spotPaint);
    }
  }

  void startSpotlight(long duration, TimeInterpolator animation,
      AbstractAnimatorListener listener) {
    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "alpha", 0f, 1f);
    objectAnimator.setDuration(duration);
    objectAnimator.setInterpolator(animation);
    objectAnimator.addListener(listener);
    objectAnimator.start();
  }

  void finishSpotlight(long duration, TimeInterpolator animation,
      AbstractAnimatorListener listener) {
    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f);
    objectAnimator.setDuration(duration);
    objectAnimator.setInterpolator(animation);
    objectAnimator.addListener(listener);
    objectAnimator.start();
  }

  void turnUp(Target target, AbstractAnimatorListener listener) {
    currentTarget = target;
    animator = ValueAnimator.ofFloat(0f, 1f);
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        SpotlightView.this.invalidate();
      }
    });
    animator.setInterpolator(target.getAnimation());
    animator.setDuration(target.getDuration());
    animator.addListener(listener);
    animator.start();
  }

  void turnDown(AbstractAnimatorListener listener) {
    if (currentTarget == null) {
      return;
    }

    animator = ValueAnimator.ofFloat(1f, 0f);
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        SpotlightView.this.invalidate();
      }
    });
    animator.addListener(listener);
    animator.setInterpolator(currentTarget.getAnimation());
    animator.setDuration(currentTarget.getDuration());
    animator.start();
  }
}
