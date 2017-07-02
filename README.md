# Spotlight
#### Android Library that lights items for tutorials or walk-throughs etc...

<img src="https://github.com/TakuSemba/Spotlight/blob/master/arts/logo.png" alt="alt text" style="width:200;height:200">

## Gradle

```groovy
dependencies {
    compile 'hogehgoe:1.0.0'
}
```


## Usage

```java

Spotlight.with(this)
        .setDuration(1000L) // duration of Spotlight emerging and disappearing in ms
        .setAnimation(new DecelerateInterpolator(2f)) // animation of Spotlight
        .setTargets(firstTarget, secondTarget, thirdTarget ...) // set targes. see below for more info
        .setOnSpotlightStartedListener(new OnSpotlightStartedListener() { // callback when Spotlight starts
            @Override
            public void onStarted() {
                Toast.makeText(context, "spotlight is started", Toast.LENGTH_SHORT).show();
            }
        })
        .setOnSpotlightEndedListener(new OnSpotlightEndedListener() { // callback when Spotlight ends
            @Override
            public void onEnded() {
                Toast.makeText(context, "spotlight is ended", Toast.LENGTH_SHORT).show();
            }
        })
        .start(); // start Spotlight
                        
```


### Simple Target
simply set a title and description, these position will automatically be calculated.

<img src="https://github.com/TakuSemba/Spotlight/blob/master/arts/simpleTarget.gif" alt="alt text" style="width:200;height:200">

```java

SimpleTarget simpleTarget = new SimpleTarget.Builder(this)
    .setPoint(100f, 340f) // position of the Target. setPoint(Point point), setPoint(View view) will work too.
    .setRadius(80f) // radius of the Target
    .setTitle("the title") // title
    .setDescription("the description") // description
    .build();

```

### Custom Target
use your own custom view.

<img src="https://github.com/TakuSemba/Spotlight/blob/master/arts/customTarget.gif" alt="alt text" style="width:200;height:200">

```java

CustomTarget customTarget = new CustomTarget.Builder(this)
    .setPoint(100f, 340f) // position of the Target. setPoint(Point point), setPoint(View view) will work too.
    .setRadius(80f) // radius of the Target
    .setView(R.layout.layout_target) // custom view
    .build();

```

### Sample
Clone this repo and check out the [app](https://github.com/TakuSemba/Spotlight/tree/master/app) module.

## Change Log

### Version: 1.0.0

  * Initial Build


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
