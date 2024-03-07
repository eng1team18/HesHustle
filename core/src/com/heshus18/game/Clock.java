package com.heshus18.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Clock {
    private BitmapFont font;
    private Vector2 position;
    private Time gameTime;

    public Clock(Vector2 position, Time gameTime) {
        this.position = position;
        this.gameTime = gameTime;
        this.font = new BitmapFont();
        this.font.setColor(Color.WHITE);
        this.font.getData().setScale(1.0f);
    }

    public void render(SpriteBatch batch) {
        // Obtaining the current day and time
        String day = gameTime.getDayName(gameTime.getDay());
        String time = String.format("%02d:%02d", gameTime.getHour(), gameTime.getMinute());

        // Draw the time HUD on screen
        font.draw(batch, day, position.x, position.y);
        font.draw(batch, time, position.x, position.y - 20);
    }

    public void dispose() {
        font.dispose();
    }
}
