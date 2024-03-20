package com.heshus18.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents an energy bar in the game, displaying the current energy.
 * The energy bar visually decreases as energy is consumed and increases when energy is gained.
 */
public class EnergyBar {
    private float energy;
    private final float maxEnergy, barWidth, barHeight;
    private final Vector2 position;
    private final Texture backgroundTexture,foregroundTexture;

    /**
     * Creates an EnergyBar instance with specified properties.
     *
     * @param maxEnergy             The maximum energy the bar can hold.
     * @param barWidth              The width of the energy bar.
     * @param barHeight             The height of the energy bar.
     * @param position              The position of the energy bar on screen.
     * @param backgroundTexturePath The file path for the background texture.
     * @param foregroundTexturePath The file path for the foreground (energy) texture.
     */
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

    /**
     * Renders the energy bar on the screen, showing the current energy level.
     *
     * @param batch The SpriteBatch used for drawing.
     */
    public void render(SpriteBatch batch) {
        batch.draw(backgroundTexture, position.x, position.y, barWidth, barHeight);

        // This allows the size of the background and foreground of the energy bar to be adjusted
        // i.e. this is the code used for a 4 pixel border
        float padding = 4f;
        float adjustedWidth = (energy / maxEnergy) * (barWidth - padding * 2); // Adjust width based on current energy
        float adjustedHeight = barHeight - padding * 2;
        float adjustedX = position.x + padding;
        float adjustedY = position.y + padding;

        //Draw the energy bar texture to the screen
        batch.draw(foregroundTexture, adjustedX, adjustedY, adjustedWidth, adjustedHeight);
    }

    /**
     * Sets the energy level of the bar.
     *
     * @param energy The new energy level.
     */
    public void setEnergy(float energy) {
        this.energy = Math.max(0, Math.min(energy, maxEnergy));
    }

    /**
     * Increases the energy level by a specified amount.
     *
     * @param amount The amount of energy to add.
     */
    public void addEnergy(float amount) {
        setEnergy(this.energy + amount);
    }

    /**
     * Decreases the energy level by a specified amount.
     *
     * @param amount The amount of energy to drain.
     * @return True if the energy was successfully drained, false if there was not enough energy.
     */
    public boolean drainEnergy(float amount) {
        if (this.energy >= amount) {
            setEnergy(this.energy - amount);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the current energy level.
     *
     * @return The current energy level.
     */
    public float getEnergy() {
        return energy;
    }

    /**
     * Disposes all loaded assets from memory after closing the game.
     */
    public void dispose() {
        backgroundTexture.dispose();
        foregroundTexture.dispose();
    }
}
