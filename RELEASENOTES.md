Release notes
==========

Version 2.0.2 **(1020-xx-xx)**
----------------------------
 - Update dependencies.
 - Provide overloaded constructors for `Shape` and `Effect` implementations. ([#87](https://github.com/TakuSemba/Spotlight/issues/87))
 - Throw an Exception when you add an empty target to Spotlight. ([#84](https://github.com/TakuSemba/Spotlight/issues/84))
 - Fix anchor is a bit off when using custom container.
 - Fix issue where SpotlightView is leaked via an animation listener. ([#93](https://github.com/TakuSemba/Spotlight/issues/93))
 - Fix behavior of RippleEffect.
 - Start Effect after Target is drawn.

Version 2.0.1 **(1019-11-11)**
----------------------------
 - Fix internal logic.

Version 2.0.0 **(1019-11-11)**
----------------------------
 - Require kotlin 1.3.50.
 - Remove SimpleTarget.
 - Remove `isClosedOnTouchedOutside` attribute.
 - Add `Spotlight.next()`, `Spotlight.previous()`, `Spotlight.show()` functionality.
   - **next**: show the next target after closing the current target if exists.
   - **previous**: show the previous target after closing the current target if exists.
   - **show**: show the target at specified index. (e.g. show(1)).
 - Add `setContainer` to set a custom container to hold a SpotlightView. The default is DecoderView.
 - Add Effect feature. `RippleEffect` and `FlickerEffect` are provided by default.
   ```kt
   val target = Target.Builder()
     .setAnchor(100f, 100f)
     .setShape(Circle(150f))
     .setEffect(FlickerEffect(200f, rgb(124, 255, 90)))
     .build()
   ```

Thank you for the feature requests from @OliverCulleyDeLange, @hamz4k.

Version 1.8.0 **(2019-1-5)**
----------------------------
 - Add Rounded rectangle shape.

Version 1.5.0 **(2018-5-13)**
----------------------------
 - Add custom shapes functionality.
 - Add skip feature.

Version 1.3.0 **(2018-2-8)**
----------------------------
 - Add click handling listener.

Version 1.2.0 **(2018-1-27)**
----------------------------
 - Add Overlay color attribute.

Version 1.0.0 **(2017-7-2)**
----------------------------
 - Initial Release.