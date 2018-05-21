package com.takusemba.spotlightsample;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.takusemba.spotlight.OnSpotlightStateChangedListener;
import com.takusemba.spotlight.OnTargetStateChangedListener;
import com.takusemba.spotlight.Spotlight;
import com.takusemba.spotlight.shape.Circle;
import com.takusemba.spotlight.target.CustomTarget;
import com.takusemba.spotlight.target.SimpleTarget;
import com.takusemba.spotlight.target.Target;

import java.util.ArrayList;

public class  MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.simple_target).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View one = findViewById(R.id.one);
                int[] oneLocation = new int[2];
                one.getLocationInWindow(oneLocation);
                float oneX = oneLocation[0] + one.getWidth() / 2f;
                float oneY = oneLocation[1] + one.getHeight() / 2f;
                // make an target
                SimpleTarget firstTarget = new SimpleTarget.Builder(MainActivity.this).setPoint(oneX, oneY)
                        .setShape(new Circle(100f))
                        .setTitle("first title")
                        .setDescription("first description")
                        .build();

                View two = findViewById(R.id.two);
                int[] twoLocation = new int[2];
                two.getLocationInWindow(twoLocation);
                PointF point =
                        new PointF(twoLocation[0] + two.getWidth() / 2f, twoLocation[1] + two.getHeight() / 2f);
                // make an target
                SimpleTarget secondTarget = new SimpleTarget.Builder(MainActivity.this).setPoint(point)
                        .setShape(new Circle(80f))
                        .setTitle("second title")
                        .setDescription("second description")
                        .setOnSpotlightStartedListener(new OnTargetStateChangedListener<SimpleTarget>() {
                            @Override
                            public void onStarted(SimpleTarget target) {
                                Toast.makeText(MainActivity.this, "target is started", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onEnded(SimpleTarget target) {
                                Toast.makeText(MainActivity.this, "target is ended", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build();

                SimpleTarget thirdTarget;

                thirdTarget = new SimpleTarget.Builder(MainActivity.this).setPoint(findViewById(R.id.three))
                        .setShape(new Circle(200f))
                        .setTitle("third title")
                        .setDescription("third description")
                        .build();

                Spotlight.with(MainActivity.this)
                        .setOverlayColor(R.color.background)
                        .setDuration(100L)
                        .setAnimation(new DecelerateInterpolator(2f))
                        .setTargets(firstTarget, secondTarget, thirdTarget)
                        .setClosedOnTouchedOutside(true)
                        .setOnSpotlightStateListener(new OnSpotlightStateChangedListener() {
                            @Override
                            public void onStarted() {
                                Toast.makeText(MainActivity.this, "spotlight is started", Toast.LENGTH_SHORT)
                                        .show();
                            }

                            @Override
                            public void onEnded() {
                                Toast.makeText(MainActivity.this, "spotlight is ended", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .start();
            }
        });

        findViewById(R.id.custom_target).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);

                ArrayList<Target> targets = new ArrayList<>();

                // make an target
                View first = inflater.inflate(R.layout.layout_target, null);
                final CustomTarget firstTarget =
                        new CustomTarget.Builder(MainActivity.this).setPoint(findViewById(R.id.one))
                                .setShape(new Circle(100f))
                                .setOverlay(first)
                                .build();

                targets.add(firstTarget);

                View second = inflater.inflate(R.layout.layout_target, null);
                final CustomTarget secondTarget =
                        new CustomTarget.Builder(MainActivity.this).setPoint(findViewById(R.id.two))
                                .setShape(new Circle(800f))
                                .setOverlay(second)
                                .build();

                targets.add(secondTarget);

                View third = inflater.inflate(R.layout.layout_target, null);
                final CustomTarget thirdTarget =
                        new CustomTarget.Builder(MainActivity.this).setPoint(findViewById(R.id.three))
                                .setShape(new Circle(200f))
                                .setOverlay(third)
                                .build();

                targets.add(thirdTarget);

                final Spotlight spotlight =

                        Spotlight.with(MainActivity.this)
                                .setOverlayColor(R.color.background)
                                .setDuration(1000L)
                                .setAnimation(new DecelerateInterpolator(2f))
                                .setTargets(targets)
                                .setClosedOnTouchedOutside(false)
                                .setOnSpotlightStateListener(new OnSpotlightStateChangedListener() {
                                    @Override
                                    public void onStarted() {
                                        Toast.makeText(MainActivity.this, "spotlight is started", Toast.LENGTH_SHORT)
                                                .show();
                                    }

                                    @Override
                                    public void onEnded() {
                                        Toast.makeText(MainActivity.this, "spotlight is ended", Toast.LENGTH_SHORT).show();
                                    }
                                });
                spotlight.start();

                View.OnClickListener closeTarget = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        spotlight.closeCurrentTarget();
                    }
                };

                View.OnClickListener closeSpotlight = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        spotlight.closeSpotlight();
                    }
                };

                first.findViewById(R.id.close_target).setOnClickListener(closeTarget);
                second.findViewById(R.id.close_target).setOnClickListener(closeTarget);
                third.findViewById(R.id.close_target).setOnClickListener(closeTarget);

                first.findViewById(R.id.close_spotlight).setOnClickListener(closeSpotlight);
                second.findViewById(R.id.close_spotlight).setOnClickListener(closeSpotlight);
                third.findViewById(R.id.close_spotlight).setOnClickListener(closeSpotlight);
            }
        });
    }
}
