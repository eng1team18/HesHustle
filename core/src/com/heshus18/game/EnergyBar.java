package com.heshus18.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class EnergyBar {
    private float energy;
    private final float maxEnergy;
    private final float barWidth;
    private final float barHeight;
    private final Vector2 position;
    private Texture backgroundTexture;
    private Texture foregroundTexture;

    public EnergyBar(float maxEnergy, float barWidth, float barHeight, Vector2 position,
                     String backgroundTexturePath, String foregroundTexturePath) {
        this.maxEnergy = maxEnergy;
        this.energy = maxEnergy;
        this.barWidth = barWidth;
        this.barHeight = barHeight;
        this.position = position;
        this.backgroundTexture = new Texture(Gdx.files.internal(backgroundTexturePath));
        this.foregroundTexture = new Texture(Gdx.files.internal(foregroundTexturePath));
    }

    public void render(SpriteBatch batch) {
        batch.draw(backgroundTexture, position.x, position.y, barWidth, barHeight);

        // This allows the size of the background and foreground of the energy bar to be adjusted
        // i.e. this is the code used for a 4 pixel border
        float padding = 4f;
        float adjustedWidth = (energy / maxEnergy) * (barWidth - padding * 2); // Adjust width based on current energy
        float adjustedHeight = barHeight - padding * 2;
        float adjustedX = position.x + padding;
        float adjustedY = position.y + padding;

        batch.draw(foregroundTexture, adjustedX, adjustedY, adjustedWidth, adjustedHeight);
    }

    public void setEnergy(float energy) {
        this.energy = Math.max(0, Math.min(energy, maxEnergy));
    }

    public void addEnergy(float amount) {
        setEnergy(this.energy + amount);
    }

    public void drainEnergy(float amount) {
        setEnergy(this.energy - amount);
    }

    public float getEnergy() {
        return energy;
    }

    public void dispose() {
        backgroundTexture.dispose();
        foregroundTexture.dispose();
    }
}
