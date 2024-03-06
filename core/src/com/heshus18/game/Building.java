package com.heshus18.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;


public class Building {
    public Rectangle bounds;
    public Texture texture;

    public Building(float x, float y, float width, float height, Texture texture) {
        this.bounds = new Rectangle(x, y, width, height);
        this.texture = texture;
    }
}

