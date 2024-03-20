package com.heshus18.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages the pop-up messages within the game, including adding, showing, updating, and rendering pop-ups.
 */
public class PopUpManager {
    private final Map<String, PopUp> popUps;

    /**
     * Initializes a new PopUpManager instance.
     */
    public PopUpManager() {
        popUps = new HashMap<String, PopUp>();
    }

    /**
     * Adds a new pop-up to the manager.
     *
     * @param popUp The pop-up to add, identified by its unique ID.
     */
    public void addPopUp(PopUp popUp) {
        popUps.put(popUp.getId(), popUp);
    }

    /**
     * Shows a pop-up by setting its visibility to true.
     *
     * @param id The unique identifier of the pop-up to show.
     */
    public void showPopUp(String id) {
        PopUp popUp = popUps.get(id);
        if (popUp != null) {
            popUp.setVisible(true);
        }
    }

    /**
     * Updates all pop-ups
     */
    public void update() {
        for (PopUp popUp : popUps.values()) {
            popUp.update();
        }
    }

    /**
     * Renders pop-ups onto the screen. With checks to ensure only visible pop-ups are rendered
     *
     * @param batch The SpriteBatch used for drawing the pop-ups.
     */
    public void render(SpriteBatch batch) {
        for (PopUp popUp : popUps.values()) {
            popUp.render(batch);
        }
    }

    /**
     * Checks if any pop-up is currently visible.
     *
     * @return True if a pop-up is visible, false otherwise.
     */
    public boolean isAnyPopUpVisible() {
        for (PopUp popUp : popUps.values()) {
            if (popUp.isVisible()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Declines visible pop-ups.
     */
    public void declineVisiblePopUp() {
        for (PopUp popUp : popUps.values()) {
            if (popUp.isVisible()) {
                popUp.decline();
            }
        }
    }

    /**
     * Updates the message of a pop-up identified by its ID.
     *
     * @param id The unique identifier of the pop-up to update.
     * @param message The new message to be set.
     */
    public void updateMessage(String id, String message) {
        PopUp popUp = popUps.get(id);
        if (popUp != null) {
            popUp.updateMessage(message);
        }
    }

    /**
     * Disposes all loaded assets from memory after closing the game.
     */
    public void dispose() {
        for (PopUp popUp : popUps.values()) {
            popUp.dispose();
        }
        popUps.clear();
    }
}