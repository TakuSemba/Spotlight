# Spotlight

<img src="https://github.com/TakuSemba/Spotlight/blob/master/arts/logo_yello.png" alt="alt text" style="width:200;height:200">

![Build Status](https://app.bitrise.io/app/bcf0d555e7b41eb2/status.svg?token=2wvl_JilEbg6HB3B1tfKpA&branch=master)
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

```kt
val spotlight = Spotlight.Builder(this)
    .setTargets(firstTarget, secondTarget, thirdTarget ...)
    .setBackgroundColor(R.color.spotlightBackground)
    .setDuration(1000L)
    .setAnimation(DecelerateInterpolator(2f))
    .setContainer(viewGroup)
    .setOnSpotlightListener(object : OnSpotlightListener {
      override fun onStarted() {
        Toast.makeText(this@MainActivity, "spotlight is started", Toast.LENGTH_SHORT).show()
      }
      override fun onEnded() {
        Toast.makeText(this@MainActivity, "spotlight is ended", Toast.LENGTH_SHORT).show()
      }
    })
    .build()         
```

If you want to show Spotlight immediately, you have to wait until views are laid out.

```kt
// with core-ktx method.
view.doOnPreDraw { Spotlight.Builder(this)...start() }
```

<br/>
<br/>

<img src="https://github.com/TakuSemba/Spotlight/blob/master/arts/customTarget.gif" align="left" width="30%">

## Target
Create a Target to add Spotlight.

Target is a spot to be casted by Spotlight. You can add multiple targets to Spotlight.

```kt
val target = Target.Builder()
    .setAnchor(100f, 100f)
    .setShape(Circle(100f))
    .setEffect(RippleEffect(100f, 200f, argb(30, 124, 255, 90)))
    .setOverlay(layout)
    .setOnTargetListener(object : OnTargetListener {
      override fun onStarted() {
        makeText(this@MainActivity, "first target is started", LENGTH_SHORT).show()
      }
      override fun onEnded() {
        makeText(this@MainActivity, "first target is ended", LENGTH_SHORT).show()
      }
    })
    .build()
```


<br/>
<br/>

## Start/Finish Spotlight

```kt
val spotlight = Spotlight.Builder(this)...start()

spotlight.finish()
```

## Next/Previous/Show Target

```kt
val spotlight = Spotlight.Builder(this)...start()

spotlight.next()

spotlight.previous()

spotlight.show(2)
```

## Custom Shape
`Shape` defines how your target will look like.
[Circle](https://github.com/TakuSemba/Spotlight/blob/master/spotlight/src/main/java/com/takusemba/spotlight/shape/Circle.kt) and [RoundedRectangle](https://github.com/TakuSemba/Spotlight/blob/master/spotlight/src/main/java/com/takusemba/spotlight/shape/RoundedRectangle.kt) shapes are already implemented, but if you want your custom shape, it's arhivable by implementing `Shape` interface.


```kt
class CustomShape(
    override val duration: Long,
    override val interpolator: TimeInterpolator
) : Shape {

  override fun draw(canvas: Canvas, point: PointF, value: Float, paint: Paint) {
    // draw your shape here.
  }
}
```

## Custom Effect
`Effect` allows you to decorates your target.
[RippleEffect](https://github.com/TakuSemba/Spotlight/blob/master/spotlight/src/main/java/com/takusemba/spotlight/effet/RippleEffect.kt) and [FlickerEffect](https://github.com/TakuSemba/Spotlight/blob/master/spotlight/src/main/java/com/takusemba/spotlight/effet/FlickerEffect.kt) shapes are already implemented, but if you want your custom effect, it's arhivable by implementing `Effect` interface.


```kt
class CustomEffect(
    override val duration: Long,
    override val interpolator: TimeInterpolator,
    override val repeatMode: Int
) : Effect {

  override fun draw(canvas: Canvas, point: PointF, value: Float, paint: Paint) {
    // draw your effect here.
  }
}
```

## Sample
Clone this repo and check out the [app](https://github.com/TakuSemba/Spotlight/tree/master/app) module.

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
