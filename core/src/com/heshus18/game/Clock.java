package com.heshus18.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents a clock in the game, displaying the current time and day.
 * The clock uses the {@link Time} class to obtain time information and renders it on screen.
 */
public class Clock {
    private final BitmapFont font;
    private final Vector2 position;
    private final Time gameTime;

    /**
     * Creates a Clock instance with a specified position and time.
     *
     * @param position The position where the clock will be rendered.
     * @param gameTime The {@link Time} instance containing the current time and day.
     */
    public Clock(Vector2 position, Time gameTime) {
        //Set position of the clock
        this.position = position;
        this.gameTime = gameTime;
        this.font = new BitmapFont();
        this.font.setColor(Color.WHITE);
        this.font.getData().setScale(1.2f);
    }

    /**
     * Draws the required components to the screen.
     * @param batch The collection of sprites being used in the game.
     */
    public void render(SpriteBatch batch) {
        //Obtaining the current day and time
        String day = gameTime.getDayName(gameTime.getDay());
        String time = String.format("%02d:%02d", gameTime.getHour(), gameTime.getMinute());

        //Draw the time HUD on screen
        font.draw(batch, day, position.x, position.y);
        font.draw(batch, time, position.x, position.y - 25);
    }

    /**
     * Disposes all loaded assets from memory after closing the game.
     */
    public void dispose() {
        font.dispose();
    }
}
