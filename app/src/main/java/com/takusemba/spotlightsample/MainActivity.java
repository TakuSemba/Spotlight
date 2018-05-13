package com.takusemba.spotlightsample;

import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.takusemba.spotlight.shapes.Circle;
import com.takusemba.spotlight.CustomTarget;
import com.takusemba.spotlight.OnSpotlightEndedListener;
import com.takusemba.spotlight.OnSpotlightStartedListener;
import com.takusemba.spotlight.OnTargetStateChangedListener;
import com.takusemba.spotlight.shapes.RoundedRectangle;
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

				if (Build.VERSION.SDK_INT >= 21)
					thirdTarget =
							new SimpleTarget.Builder(MainActivity.this)
									.setShape(new RoundedRectangle(findViewById(R.id.three), 40f, 20f))
									.setTitle("third title")
									.setDescription("third description")
									.build();
				else
					thirdTarget =
							new SimpleTarget.Builder(MainActivity.this)
									.setShape(new Circle(findViewById(R.id.three)))
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


                // make an target
                View first = inflater.inflate(R.layout.layout_target, null);
                final CustomTarget firstTarget =
                        new CustomTarget.Builder(MainActivity.this).setPoint(findViewById(R.id.one))
                                .setShape(new Circle(findViewById(R.id.one)))
                                .setOverlay(first)
                                .build();

                View second = inflater.inflate(R.layout.layout_target, null);
                final CustomTarget secondTarget =
                        new CustomTarget.Builder(MainActivity.this).setPoint(findViewById(R.id.two))
		                        .setShape(new Circle(findViewById(R.id.two)))
                                .setOverlay(second)
                                .build();

                View third = inflater.inflate(R.layout.layout_target, null);
                final CustomTarget thirdTarget =
                        new CustomTarget.Builder(MainActivity.this).setPoint(findViewById(R.id.three))
		                        .setShape(new Circle(findViewById(R.id.three)))
                                .setOverlay(third)
                                .build();

                final Spotlight spotlight =

                Spotlight.with(MainActivity.this)
                        .setOverlayColor(ContextCompat.getColor(MainActivity.this, R.color.background))
                        .setDuration(1000L)
                        .setAnimation(new DecelerateInterpolator(2f))
                        .setTargets(firstTarget, secondTarget,thirdTarget)
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
                        });
                        spotlight.start();View.OnClickListener closeTarget = new View.OnClickListener() {
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
