package com.heshus18.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * The credit screen displayed when the player finishes the game.
 * This screen shows the credits and options to return to the main menu.
 */
public class CreditsScreen implements Screen {
    final HesHustle game;
    OrthographicCamera camera;
    SpriteBatch batch;
    BitmapFont font;
    private final Texture mainMenuButtonTexture;
    private final Rectangle mainMenuButtonBounds;

    /**
     * Constructs the credits screen and initializes UI components.
     *
     * @param game The game instance this screen is a part of.
     */
    public CreditsScreen(final HesHustle game) {
        this.game = game;
        //Create camera to draw UI
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        //Create a new spritebatch to store sprites, and font
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2f);

        //Load main menu button texture
        mainMenuButtonTexture = new Texture(Gdx.files.internal("mainMenuButton.png"));

        //Initialise button size variables
        float buttonWidth = 300;
        float buttonHeight = 50;
        float padding = 50;
        float posX = (800 - buttonWidth) / 2;
        float replayYPos = ((float) 480 / 4) - padding;

        //Create button box for menu button
        mainMenuButtonBounds = new Rectangle(posX, replayYPos, buttonWidth, buttonHeight);
    }

    /**
     * Draws the required components to the screen.
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        //Draw solid colour background
        ScreenUtils.clear(0.3765f, 0.4588f, 0.5882f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        //Draw credits text
        batch.begin();
        font.draw(batch, "Credits", 300, 400);
        font.draw(batch, "Izz Abd Aziz\nPhil Suwanpimolkul\nTom Loomes\nOwen Kilpatrick\nZachary Vickers\nMichael Ballantyne", 300, 350);

        //Draw button textures on top of button boxes
        batch.draw(mainMenuButtonTexture, mainMenuButtonBounds.x, mainMenuButtonBounds.y, mainMenuButtonBounds.width, mainMenuButtonBounds.height);
        batch.end();

        //Detect button presses, and return to main menu screen if touched
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if (mainMenuButtonBounds.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        }
    }

    /**
     * Allows the game window to be resized by the player.
     * @param width Width of the game window.
     * @param height Height of the game Window.
     */
    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    /**
     * Disposes all loaded assets from memory after closing the game.
     */
    @Override
    public void dispose() {
        batch.dispose();
        mainMenuButtonTexture.dispose();
        font.dispose();
    }

    @Override
    public void show() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}