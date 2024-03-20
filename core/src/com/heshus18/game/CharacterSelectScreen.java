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
 * Shows a screen with choice between character sprites.
 * Click on one sprite to play as them for the game.
 * After choosing a sprite, begin the game.
 */
public class CharacterSelectScreen implements Screen {
    final HesHustle game;
    OrthographicCamera camera;
    SpriteBatch batch;
    BitmapFont font;
    private final Rectangle kenzieButtonBounds, isabelleButtonBounds;

    private final Texture kenzie, isabelle;

    /**
     * Constructs character select screen and initialises UI components
     * @param game The game instance this screen is a part of.
     */
    public CharacterSelectScreen(HesHustle game){
        this.game = game;
        //Create camera to draw UI
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        //Create texture models for sprite choices
        kenzie = new Texture(Gdx.files.internal("KenziePreview.png"));
        isabelle = new Texture(Gdx.files.internal("IsabellePreview.png"));

        //Initialise font size
        font = new BitmapFont();
        font.getData().setScale(2f);

        //Initialise button box variables for characters
        float buttonWidth = 384;
        float buttonHeight = 384;
        float padding = 10;
        float posXLight = 0; //Kenzie button position
        float posXDark = 800 - buttonWidth; //Isabelle button position
        float posY = 20; //Button height

        //Create buttons for characters
        kenzieButtonBounds = new Rectangle(posXLight, posY, buttonWidth, buttonHeight);
        isabelleButtonBounds = new Rectangle(posXDark, posY, buttonWidth, buttonHeight);
    }

    /**
     * Draws the required components to the screen.
     * @param delta The time in seconds since the last render.
     */
    public void render(float delta){
        //Draw solid colour to background
        ScreenUtils.clear(0.3765f, 0.4588f, 0.5882f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        //Draw "Choose Character" text and sprite models to the screen
        batch.begin();
        font.draw(batch, "Choose Character", 280, 450);
        batch.draw(kenzie, kenzieButtonBounds.x, kenzieButtonBounds.y,
                kenzieButtonBounds.width, kenzieButtonBounds.height);
        batch.draw(isabelle, isabelleButtonBounds.x, isabelleButtonBounds.y,
                isabelleButtonBounds.width, isabelleButtonBounds.height);
        batch.end();

        //Detect click on each character, and create a new instance of GameScreen with the corresponding character
        //sprite.
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if (kenzieButtonBounds.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new GameScreen(game, "KenzieSpriteSheet.png"));
                dispose();
            } else if (isabelleButtonBounds.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new GameScreen(game, "IsabelleSpriteSheet.png"));
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
        isabelle.dispose();
        kenzie.dispose();
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

