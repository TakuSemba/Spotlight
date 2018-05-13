package com.takusemba.spotlight;

/**
 * On Spotlight Ended Listener
 *
 * @author takusemba
 * @since 26/06/2017
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
