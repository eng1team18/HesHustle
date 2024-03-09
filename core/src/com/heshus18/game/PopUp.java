package com.heshus18.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for the pop-up window in the game that can display a message and have optional confirm and decline buttons.
 * The pop-up can be customized to show different types of messages and actions based on the type specified.
 */
public class PopUp {
    private String id;
    private boolean isVisible;
    private String message;
    private Texture backgroundTexture;
    private Rectangle bounds;
    private Texture confirmButtonTexture;
    private Rectangle confirmButtonBounds;
    private Texture declineButtonTexture;
    private Rectangle declineButtonBounds;
    private Texture doneButtonTexture;
    private Rectangle doneButtonBounds;
    private BitmapFont font;
    private List<Runnable> confirmActions;
    private OrthographicCamera camera;
    private String type;

    /**
     * Constructs a new PopUp with specified parameters.
     *
     * @param id        Unique identifier for the pop-up.
     * @param message   The message to be displayed on the pop-up.
     * @param x         The x coordinate for the pop-up position.
     * @param y         The y coordinate for the pop-up position.
     * @param width     The width of the pop-up.
     * @param height    The height of the pop-up.
     * @param camera    The game's camera, should be "hudCamera" for most of the time
     * @param type      The type of the pop-up, either "prompt" for Confirm or Deny button pop-up or "warning" for a Close button only pop-up
     */
    public PopUp(String id, String message, float x, float y, float width, float height, OrthographicCamera camera, String type) {
        this.id = id;
        this.isVisible = false;
        this.message = message;
        this.backgroundTexture = new Texture(Gdx.files.internal("popUp.png"));
        this.bounds = new Rectangle(x, y, width, height);
        this.confirmButtonTexture = new Texture(Gdx.files.internal("confirmButton.png"));
        this.confirmButtonBounds = new Rectangle(x + 20, y + 20, 150, 40);
        this.declineButtonTexture = new Texture(Gdx.files.internal("declineButton.png"));
        this.declineButtonBounds = new Rectangle(x + width - 170, y + 20, 150, 40);
        this.doneButtonTexture = new Texture(Gdx.files.internal("doneButton.png"));
        this.doneButtonBounds = new Rectangle(x + width - 170, y + 20, 150, 40);
        this.font = new BitmapFont();
        this.confirmActions = new ArrayList<>();
        this.camera = camera;
        this.type = type;
    }

    /**
     * Adds actions to be executed when the confirm button is pressed.
     *
     * @param action The actions to be added.
     */
    public void addConfirmAction(Runnable action) {
        confirmActions.add(action);
    }

    /**
     * Renders the pop-up on the screen if it is visible.
     *
     * @param batch The sprite batch used for drawing.
     */
    public void render(SpriteBatch batch) {
        if (!isVisible) return;

        batch.draw(backgroundTexture, bounds.x, bounds.y, bounds.width, bounds.height);
        font.draw(batch, message, bounds.x + 20, bounds.y + bounds.height - 20);
        if (type.equals("warning")) {
            batch.draw(doneButtonTexture, doneButtonBounds.x, doneButtonBounds.y, doneButtonBounds.width, doneButtonBounds.height);
        }
        else{
            batch.draw(confirmButtonTexture, confirmButtonBounds.x, confirmButtonBounds.y, confirmButtonBounds.width, confirmButtonBounds.height);
            batch.draw(declineButtonTexture, declineButtonBounds.x, declineButtonBounds.y, declineButtonBounds.width, declineButtonBounds.height);
        }
    }

    /**
     * Checks if the pop-up is currently visible.
     *
     * @return True if the pop-up is visible, false otherwise.
     */
    public boolean isVisible() {
        return this.isVisible;
    }

    /**
     * Updates all pop-ups
     */
    public void update() {
        if (!isVisible) return;

        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if (type.equals("prompt")) {
                if (confirmButtonBounds.contains(touchPos.x, touchPos.y)) {
                    for (Runnable action : confirmActions) {
                        action.run();
                    }
                    isVisible = false;
                    return;
                }
            }

            if (declineButtonBounds.contains(touchPos.x, touchPos.y)) {
                isVisible = false;
            }
        }
    }

    /**
     * Sets the visibility of the pop-up.
     *
     * @param visible The desired visibility state, true or false.
     */
    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    /**
     * Gets the unique identifier for the pop-up.
     *
     * @return The unique identifier.
     */
    public String getId() {
        return id;
    }

    /**
     * Hides the pop-up without executing any actions.
     */
    public void decline() {
        this.isVisible = false;
    }

    public void dispose() {
        backgroundTexture.dispose();
        confirmButtonTexture.dispose();
        declineButtonTexture.dispose();
        doneButtonTexture.dispose();
        font.dispose();
    }
}