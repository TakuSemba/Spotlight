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


## Usage

```java

Spotlight.with(this)
        .setOverlayColor(R.color.background)
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
simply set a title and description, these position will be automatically calculated.

```java

SimpleTarget simpleTarget = new SimpleTarget.Builder(this)
    .setPoint(100f, 340f)
    .setShape(new Circle(200f)) // or RoundedRectangle()
    .setTitle("the title")
    .setDescription("the description")
    .setOverlayPoint(100f, 100f)
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
use your own custom view.

```java

CustomTarget customTarget = new CustomTarget.Builder(this)
    .setPoint(100f, 340f)
    .setShape(new Circle(200f)) // or RoundedRectangle()
    .setOverlay(view)
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

you can skip the current target or skip the all comming targets.

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

### Sample
Clone this repo and check out the [app](https://github.com/TakuSemba/Spotlight/tree/master/app) module.

## Change Log

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
