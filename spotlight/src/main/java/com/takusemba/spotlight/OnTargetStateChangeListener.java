package com.takusemba.spotlight;


public interface OnTargetStateChangeListener {
    /**
     * Called when target is started
     */
    void onStarted(int index, Target target);

    /**
     * Called when target is ended
     */
    void onEnded(int index, Target target);
}
