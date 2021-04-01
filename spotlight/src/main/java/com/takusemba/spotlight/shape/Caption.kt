package com.takusemba.spotlight.shape

import android.graphics.PointF
import android.view.View

class Caption(
    val view: View,
    val type: CaptionType = CaptionType.BEFORE,
    val margins: Margins = Margins(),
    val orientation: CaptionOrientation = CaptionOrientation.HORIZONTAL,
    val anchor: PointF? = null
) {
  fun totalParentMargin() = (margins.parentBefore ?: 0) + (margins.parentAfter ?: 0)
}

enum class CaptionType(val shiftCoefficient: Int) {
  BEFORE(-1), AFTER(1)
}

enum class CaptionOrientation {
  HORIZONTAL
}
