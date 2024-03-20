package com.heshus18.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * The main menu screen of the game, allowing the player to start or exit the game.
 * It handles the rendering and interaction with the start and exit buttons.
 */
public class MainMenuScreen implements Screen {
    final HesHustle game;
    OrthographicCamera camera;
    SpriteBatch batch;

    private final Texture startGameButtonTexture, exitButtonTexture, creditButtonTexture;
    private final Rectangle startGameButtonBounds, exitButtonBounds, creditButtonBounds;

    /**
     * Constructs the main menu screen with references to the game instance, and initializes UI components.
     *
     * @param game The game instance this screen is a part of.
     */
    public MainMenuScreen(final HesHustle game) {
        this.game = game;
        //Create camera and sprite batch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        //Load textures for menu buttons
        startGameButtonTexture = new Texture(Gdx.files.internal("startButton.png"));
        exitButtonTexture = new Texture(Gdx.files.internal("exitButton.png"));
        creditButtonTexture = new Texture(Gdx.files.internal("creditsButton.png"));

        //Initialise button position variables
        float buttonWidth = 300;
        float buttonHeight = 50;
        float padding = 10;
        float posX = (800 - buttonWidth) / 2;
        float startYPos = (480 / 2) + buttonHeight + padding; // Start Game button position
        float creditYPos = (480 / 2) - (buttonHeight / 2); // Credits button position
        float exitYPos = (480 / 2) - buttonHeight - padding - buttonHeight; // Exit button position

        //Create button boxes
        startGameButtonBounds = new Rectangle(posX, startYPos, buttonWidth, buttonHeight);
        exitButtonBounds = new Rectangle(posX, exitYPos, buttonWidth, buttonHeight);
        creditButtonBounds = new Rectangle(posX, creditYPos, buttonWidth, buttonHeight);
    }

    @Override
    public void show() {}

    /**
     * Draws the required components to the screen.
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        //Draw solid background colour
        ScreenUtils.clear(0.3765f, 0.4588f, 0.5882f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        //Draw button textures onto button boxes
        batch.begin();
        batch.draw(startGameButtonTexture, startGameButtonBounds.x, startGameButtonBounds.y, startGameButtonBounds.width, startGameButtonBounds.height);
        batch.draw(creditButtonTexture, creditButtonBounds.x, creditButtonBounds.y, creditButtonBounds.width, creditButtonBounds.height);
        batch.draw(exitButtonTexture, exitButtonBounds.x, exitButtonBounds.y, exitButtonBounds.width, exitButtonBounds.height);
        batch.end();

        //Detect button presses for each button, and send to corresponding screen
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if (startGameButtonBounds.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new CharacterSelectScreen(game));
                dispose();
            } else if (creditButtonBounds.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new CreditsScreen(game));
                dispose();
            } else if (exitButtonBounds.contains(touchPos.x, touchPos.y)) {
                dispose();
                Gdx.app.exit();
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
        startGameButtonTexture.dispose();
        exitButtonTexture.dispose();
        creditButtonTexture.dispose();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}