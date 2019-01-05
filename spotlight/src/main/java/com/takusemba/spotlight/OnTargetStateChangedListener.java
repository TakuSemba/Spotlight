package com.takusemba.spotlight;

import com.takusemba.spotlight.target.Target;

/**
 * On Target State Changed Listener
 **/
public interface OnTargetStateChangedListener<T extends Target> {
  /**
   * Called when Target is started
   */
  void onStarted(T target);

  /**
   * Called when Target is started
   */
  void onEnded(T target);
}
