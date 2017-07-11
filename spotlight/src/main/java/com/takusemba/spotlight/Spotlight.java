package com.takusemba.spotlight;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Spotlight
 *
 * @author takusemba
 * @since 26/06/2017
 **/
public class Spotlight {

    /**
     * Duration of Spotlight emerging
     */
    private static final long START_SPOTLIGHT_DURATION = 500L;
    /**
     * Duration of Spotlight disappearing
     */
    private static final long FINISH_SPOTLIGHT_DURATION = 500L;

    private static final long DEFAULT_DURATION = 1000L;
    private static final TimeInterpolator DEFAULT_ANIMATION = new DecelerateInterpolator(2f);

    private static WeakReference<SpotlightView> spotlightViewWeakReference;
    private static WeakReference<Activity> contextWeakReference;
    private ArrayList<Target> targets;
    private long duration = DEFAULT_DURATION;
    private TimeInterpolator animation = DEFAULT_ANIMATION;
    private OnSpotlightStartedListener startedListener;
    private OnSpotlightEndedListener endedListener;

    private int backgroundColor = -1;

    /**
     * Constructor
     *
     * @param activity Activity to create Spotlight
     */
    private Spotlight(Activity activity) {
        contextWeakReference = new WeakReference<>(activity);
    }

    /**
     * Create Spotlight with activity reference
     *
     * @param activity Activity to create Spotlight
     * @return This Spotlight
     */
    public static Spotlight with(@NonNull Activity activity) {
        return new Spotlight(activity);
    }

    /**
     * Return context weak reference
     *
     * @return the activity
     */
    private static Context getContext() {
        return contextWeakReference.get();
    }

    /**
     * Returns {@link SpotlightView} weak reference
     *
     * @return the SpotlightView
     */
    private static SpotlightView getSpotlightView() {
        return spotlightViewWeakReference.get();
    }

    /**
     * sets {@link Target}s to Spotlight
     *
     * @param targets targets to show
     * @return the SpotlightView
     */
    public <T extends Target> Spotlight setTargets(@NonNull T... targets) {
        this.targets = new ArrayList<Target>(Arrays.asList(targets));
        return this;
    }

    /**
     * sets duration to {@link Target} Animation
     *
     * @param duration duration of Target Animation
     * @return the SpotlightView
     */
    public Spotlight setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    /**
     * sets duration to {@link Target} Animation
     *
     * @param animation type of Target Animation
     * @return the SpotlightView
     */
    public Spotlight setAnimation(TimeInterpolator animation) {
        this.animation = animation;
        return this;
    }

    /**
     * Sets Spotlight start Listener to Spotlight
     *
     * @param listener OnSpotlightStartedListener of Spotlight
     * @return This Spotlight
     */
    public Spotlight setOnSpotlightStartedListener(
            @NonNull final OnSpotlightStartedListener listener) {
        startedListener = listener;
        return this;
    }

    /**
     * Sets Spotlight end Listener to Spotlight
     *
     * @param listener OnSpotlightEndedListener of Spotlight
     * @return This Spotlight
     */
    public Spotlight setOnSpotlightEndedListener(@NonNull final OnSpotlightEndedListener listener) {
        endedListener = listener;
        return this;
    }

    public Spotlight setBackgroundColor(@ColorInt int backgroundColor)
    {
        this.backgroundColor = backgroundColor;
        return this;
    }

    /**
     * Shows {@link SpotlightView}
     */
    public void start() {
        spotlightView();
    }

    /**
     * Creates the spotlight view and starts
     */
    private void spotlightView() {
        if (getContext() == null) {
            throw new RuntimeException("context is null");
        }
        final View decorView = ((Activity) getContext()).getWindow().getDecorView();
        SpotlightView spotlightView = new SpotlightView(getContext());
        spotlightView.setBackgroundColor(backgroundColor);
        spotlightViewWeakReference = new WeakReference<>(spotlightView);
        spotlightView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        ((ViewGroup) decorView).addView(spotlightView);
        spotlightView.setOnSpotlightStateChangedListener(new SpotlightView.OnSpotlightStateChangedListener() {
            @Override
            public void onTargetClosed() {
                if (targets != null && targets.size() > 0) {
                    startTarget();
                } else {
                    finishSpotlight();
                }
            }

            @Override
            public void onTargetClicked() {
                finishTarget();
            }
        });
        startSpotlight();
    }

    /**
     * show Target
     */
    private void startTarget() {
        if (targets != null && targets.size() > 0) {
            Target target = targets.get(0);
            getSpotlightView().removeAllViews();
            getSpotlightView().addView(target.getView());
            getSpotlightView().turnUp(target.getPoint().x, target.getPoint().y, target.getRadius(),
                    duration, animation);
        }
    }

    /**
     * show Spotlight
     */
    private void startSpotlight() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(getSpotlightView(), "alpha", 0f, 1f);
        objectAnimator.setDuration(START_SPOTLIGHT_DURATION);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (startedListener != null) startedListener.onStarted();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startTarget();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.start();
    }

    /**
     * hide Target
     */
    private void finishTarget() {
        if (targets != null && targets.size() > 0) {
            Target target = targets.remove(0);
            getSpotlightView().turnDown(target.getRadius(), duration, animation);
        }
    }

    /**
     * hide Spotlight
     */
    private void finishSpotlight() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(getSpotlightView(), "alpha", 1f, 0f);
        objectAnimator.setDuration(FINISH_SPOTLIGHT_DURATION);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                final View decorView = ((Activity) getContext()).getWindow().getDecorView();
                ((ViewGroup) decorView).removeView(getSpotlightView());
                if (endedListener != null) endedListener.onEnded();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.start();
    }
}
