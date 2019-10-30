package com.takusemba.spotlight.target;

import android.graphics.PointF;

/**
 * Used when point calculations need to be deferred
 */
public interface PointSupplier {

  /**
   * returns the point
   */
  PointF get();
}
