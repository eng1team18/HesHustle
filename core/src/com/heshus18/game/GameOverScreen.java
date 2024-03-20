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
 * The game over screen displayed when the player finishes the game.
 * This screen shows the final score counter and options to return to the main menu or exit the game.
 */
public class GameOverScreen implements Screen {
    final HesHustle game;
    OrthographicCamera camera;
    SpriteBatch batch;
    BitmapFont font;
    private final Texture mainMenuButtonTexture, exitButtonTexture;
    private final Rectangle mainMenuButtonBounds, exitButtonBounds;
    private final Score score;

    /**
     * Constructs the game over screen with a reference to the game instance and initializes UI components.
     *
     * @param game The game instance this screen is a part of.
     */
    public GameOverScreen(final HesHustle game) {
        this.game = game;
        //Create camera to draw UI
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        //Create a new spritebatch to store sprites, and font

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2f);

        //Load textures for menu buttons
        mainMenuButtonTexture = new Texture(Gdx.files.internal("mainMenuButton.png"));
        exitButtonTexture = new Texture(Gdx.files.internal("exitButton.png"));

        //Initialise position variables for menu button boxes
        float buttonWidth = 300;
        float buttonHeight = 50;
        float padding = 10;
        float posX = (800 - buttonWidth) / 2;
        float replayYPos = (480 / 4) + padding;
        float exitYPos = (480 / 4) - buttonHeight - padding;

        //Draw menu button textures onto button boxes
        mainMenuButtonBounds = new Rectangle(posX, replayYPos, buttonWidth, buttonHeight);
        exitButtonBounds = new Rectangle(posX, exitYPos, buttonWidth, buttonHeight);

        this.score = Score.getInstance();
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

        //Draw "GAME OVER" text, and list of stats
        batch.begin();
        font.draw(batch, "GAME OVER", 300, 400);
        String scoresText = String.format(
                "Time Ate: %d\nTime Slept: %d\nTime Study: %d\nTime Activity: %d",
                score.getTimeAte(), score.getTimeSlept(), score.getTimeStudy(), score.getTimeActivity());
        font.draw(batch, scoresText, 300, 350);

        //Draw button textures on top of button boxes
        batch.draw(mainMenuButtonTexture, mainMenuButtonBounds.x, mainMenuButtonBounds.y, mainMenuButtonBounds.width, mainMenuButtonBounds.height);
        batch.draw(exitButtonTexture, exitButtonBounds.x, exitButtonBounds.y, exitButtonBounds.width, exitButtonBounds.height);
        batch.end();

        //Detect inputs for button presses, and send player to main menu or close game accordingly
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if (mainMenuButtonBounds.contains(touchPos.x, touchPos.y)) {
                score.resetCounters();
                game.setScreen(new MainMenuScreen(game));
                dispose();
            } else if (exitButtonBounds.contains(touchPos.x, touchPos.y)) {
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
        mainMenuButtonTexture.dispose();
        exitButtonTexture.dispose();
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