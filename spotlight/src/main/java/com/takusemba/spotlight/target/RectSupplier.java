package com.takusemba.spotlight.target;

import android.graphics.Rect;

/**
 * Used when rect calculations need to be deferred
 */
public interface RectSupplier {

  /**
   * returns the rect
   */
  Rect get();
}
