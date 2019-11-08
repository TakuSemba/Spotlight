# Spotlight

<img src="https://github.com/TakuSemba/Spotlight/blob/master/arts/logo_yello.png" alt="alt text" style="width:200;height:200">

![Platform](http://img.shields.io/badge/platform-android-green.svg?style=flat)
![Download](https://api.bintray.com/packages/takusemba/maven/spotlight/images/download.svg)
![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)
![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)

## Gradle

```groovy
dependencies {
    implementation 'com.github.takusemba:spotlight:x.x.x'
}
```


## Basic Usage

```java

Spotlight.with(this)
        .setOverlayColor(R.color.spotlight_background)
        .setDuration(1000L)
        .setAnimation(new DecelerateInterpolator(2f))
        .setTargets(firstTarget, secondTarget, thirdTarget ...)
        .setClosedOnTouchedOutside(false)
        .setOnSpotlightStateListener(new OnSpotlightStateChangedListener() {
            @Override
            public void onStarted() {
                Toast.makeText(MainActivity.this, "spotlight is started", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEnded() {
                Toast.makeText(MainActivity.this, "spotlight is ended", Toast.LENGTH_SHORT).show();
            }
        })
        .start();
                        
```

if you want to show Spotlight immediately, use `addOnGlobalLayoutListener` to wait until views are drawn.

```java
view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
    @Override public void onGlobalLayout() {
        view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        Spotlight.with(this)...start();
    }
});
```

<br/>
<br/>

<img src="https://github.com/TakuSemba/Spotlight/blob/master/arts/simpleTarget.gif" align="left" width="30%">

## Simple Target
This is a built in `Target` which has a title and subtitle.
Intended to get you up and running quickly, but lacks customisability.

```java

SimpleTarget simpleTarget = new SimpleTarget.Builder(this)
    .setRectFromView(R.id.your_view_id) // Spotlight will light up this view
    .setShape(new Circle(20)) // or RoundedRectangle(). 20 is the amount of padding around the view
    .setTitle("the title")
    .setDescription("the description")
    .setOverlayPadding(new Padding(100,100)) // This adds (x,y) padding to the overlay view, so you can move the text around
    .setOnSpotlightStartedListener(new OnTargetStateChangedListener<SimpleTarget>() {
        @Override
        public void onStarted(SimpleTarget target) {
            // do something
        }
        @Override
        public void onEnded(SimpleTarget target) {
            // do something
        }
    })
    .build();

```

<br/>
<br/>

<img src="https://github.com/TakuSemba/Spotlight/blob/master/arts/customTarget.gif" align="left" width="30%">

## Custom Target
Use your own custom view with Spotlight.

```java

CustomTarget customTarget = new CustomTarget.Builder(this)
    //.setRectFromView(R.id.your_view_id) // Gets the views Rect when the CustomTarget.Builder.build() method is called
    .setRectSupplierFromView(R.id.your_view_id) // Gets the views Rect just before the Target starts
    .setShape(new RoundedRectangle(Padding(10,10), 20)
    .setOverlay(View.inflate(this, R.layout.your_custom_view, null))
    .setOnSpotlightStartedListener(new OnTargetStateChangedListener<CustomTarget>() {
        @Override
        public void onStarted(CustomTarget target) {
            // do something
        }
        @Override
        public void onEnded(CustomTarget target) {
            // do something
        }
    })
    .build();

```


<br/>
<br/>

## Skip Target, Skip Spotlight

you can skip the current target or skip the all coming targets.

```java
Spotlight spotlight = Spotlight.with(this)...start();

spotlight.closeCurrentTarget();

spotlight.closeSpotlight();
```

## Custom Shape
custom shape is available implementing Shape interface


```java
public class YourShape implements Shape {

    private float width;
    private float height;

    public YourShape(float width, float height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Canvas canvas, PointF point, float value, Paint paint) {
        // draw your shape using canvas.
    }
    ...
}

```

<br/>

## Defer Position Calculation
Sometimes, the view we want to spotlight doesn't exist or is in a different position when we build and run Spotlight.
In this case, we can defer the position calculation using RectSuppliers. The supplier will be called just before the Target starts.

```java

CustomTarget customTarget = new CustomTarget.Builder(this)
    //.setRectSupplierFromView(R.id.your_view_id) // Gets the views Rect just before the Target starts
    .setRectSupplier(new RectSupplier() {
           @Override public Rect get() {
           // This is basically what the setRectSupplierFromView method does...
            View view = findViewById(R.id.view_that_will_appear_soon);
            Rect viewBounds = new Rect();
            view.getDrawingRect(viewBounds);
            View root = findViewById(android.R.id.content);
            root.offsetDescendantRectToMyCoords(view, viewBounds);
            return viewBounds;
           }
         })
       .setShape(new RoundedRectangle(new Padding(10,10), 20))
    .setOverlay(View.inflate(this, R.layout.your_custom_view, null))
    .build();

```

## Spotlight a static view in the CustomTarget layout file
Sometimes, we might want to spotlight a particular area of the screen, and have our overlay be responsive.
We can use an ID from the overlay layout xml file to target.

```xml custom_view

<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
  xmlns:app="http://schemas.android.com/apk/res-auto"
  android:layout_width="match_parent"
  android:layout_height="match_parent"
  tools:background="@color/spotlight_background"
  >

  <TextView
    android:id="@+id/custom_text"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginBottom="8dp"
    android:gravity="center"
    android:text="Some tutorial text here"
    android:textAlignment="center"
    android:textColor="@android:color/white"
    android:textSize="24dp"
    android:textStyle="bold"
    app:layout_constraintBottom_toTopOf="@+id/close_target"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    />


  <View
    android:id="@+id/spotlight_placeholder" <- We'll use this!
    android:layout_width="0dp"
    android:layout_height="0dp"
    android:layout_marginStart="32dp"
    android:layout_marginLeft="32dp"
    android:layout_marginTop="32dp"
    android:layout_marginEnd="32dp"
    android:layout_marginRight="32dp"
    android:layout_marginBottom="32dp"
    app:layout_constraintBottom_toTopOf="@+id/custom_text"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toTopOf="parent"
    />

</androidx.constraintlayout.widget.ConstraintLayout>

```


```java

CustomTarget customTarget = new CustomTarget.Builder(this)
    .setRectSupplierFromView(R.id.spotlight_placeholder)
    .setShape(new RoundedRectangle())
    .setOverlay(View.inflate(this, R.layout.custom_view, null))
    .build();

```

### Troubleshooting
If something isn't working, check out the example app - clone this repo and run the [app](https://github.com/TakuSemba/Spotlight/tree/master/app) module. Or check out the source code.

## Change Log

### Version 2.0.0
  * Draw Spotlight in root of activity, instead of DecorView
  * Allow deferring position calculation to allow spotlighting views that may not be present at first
  * Use Rect and padding instead of PointF and shape size as we don't always know the size of the spotlighted element
  * Allow manually triggering the next target to allow actions to take place inbetween targets.
  * Allow using views in the custom layout as the target view

### Version: 1.8.0

  * rounded rectangle is added
  
### Version: 1.5.0

  * custom shapes, skip feature
  
### Version: 1.3.0

  * click handling added

### Version: 1.2.0

  * overlay color added

## Author

* **Taku Semba**
    * **Github** - (https://github.com/takusemba)
    * **Twitter** - (https://twitter.com/takusemba)
    * **Facebook** - (https://www.facebook.com/takusemba)

## Licence
```
Copyright 2017 Taku Semba.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
