package com.takusemba.spotlightsample;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.takusemba.spotlight.CustomTarget;
import com.takusemba.spotlight.OnSpotlightEndedListener;
import com.takusemba.spotlight.OnSpotlightStartedListener;
import com.takusemba.spotlight.OnTargetStateChangedListener;
import com.takusemba.spotlight.SimpleTarget;
import com.takusemba.spotlight.Spotlight;

public class MainActivity extends AppCompatActivity {

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
                        .setRadius(100f)
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
                        .setRadius(80f)
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

                SimpleTarget thirdTarget =
                        new SimpleTarget.Builder(MainActivity.this).setPoint(findViewById(R.id.three))
                                .setRadius(200f)
                                .setTitle("third title")
                                .setDescription("third description")
                                .build();

                Spotlight.with(MainActivity.this)
                        .setOverlayColor(ContextCompat.getColor(MainActivity.this, R.color.background))
                        .setDuration(1000L)
                        .setAnimation(new DecelerateInterpolator(2f))
                        .setTargets(firstTarget, secondTarget, thirdTarget)
                        .setClosedOnTouchedOutside(true)
                        .setOnSpotlightStartedListener(new OnSpotlightStartedListener() {
                            @Override
                            public void onStarted() {
                                Toast.makeText(MainActivity.this, "spotlight is started", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        })
                        .setOnSpotlightEndedListener(new OnSpotlightEndedListener() {
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
                View view = inflater.inflate(R.layout.layout_target, null);

                // make an target
                final CustomTarget thirdTarget =
                        new CustomTarget.Builder(MainActivity.this).setPoint(findViewById(R.id.three))
                                .setRadius(200f)
                                .setView(view)
                                .build();

                view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        thirdTarget.closeTarget();
                    }
                });

                Spotlight.with(MainActivity.this)
                        .setOverlayColor(ContextCompat.getColor(MainActivity.this, R.color.background))
                        .setDuration(1000L)
                        .setAnimation(new DecelerateInterpolator(2f))
                        .setTargets(thirdTarget)
                        .setClosedOnTouchedOutside(false)
                        .setOnSpotlightStartedListener(new OnSpotlightStartedListener() {
                            @Override
                            public void onStarted() {
                                Toast.makeText(MainActivity.this, "spotlight is started", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        })
                        .setOnSpotlightEndedListener(new OnSpotlightEndedListener() {
                            @Override
                            public void onEnded() {
                                Toast.makeText(MainActivity.this, "spotlight is ended", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .start();
            }
        });
    }
}
