package com.takusemba.spotlight;

/**
 * On Spotlight Ended Listener
 **/
public interface OnSpotlightStateChangedListener {

    /**
     * Called when Spotlight is started
     */
    void onStarted();

    /**
     * Called when Spotlight is ended
     */
    void onEnded();
}
