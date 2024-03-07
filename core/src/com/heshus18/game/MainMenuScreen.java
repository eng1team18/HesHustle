package com.heshus18.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    final HesHustle game;
    OrthographicCamera camera;
    SpriteBatch batch;

    private Texture startGameButtonTexture;
    private Rectangle startGameButtonBounds;
    private Texture creditsButtonTexture;
    private Rectangle creditsButtonBounds;
    private Texture exitButtonTexture;
    private Rectangle exitButtonBounds;

    public MainMenuScreen(final HesHustle game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        startGameButtonTexture = new Texture(Gdx.files.internal("startButton.png"));
        creditsButtonTexture = new Texture(Gdx.files.internal("creditsButton.png"));
        exitButtonTexture = new Texture(Gdx.files.internal("exitButton.png"));

        float buttonWidth = 300;
        float buttonHeight = 50;
        float padding = 10;
        float posX = (800 - buttonWidth) / 2;
        float startYPos = (480 / 2) + buttonHeight + padding; // Start Game button position
        float creditsYPos = (480 / 2) - (buttonHeight / 2); // Credits button position
        float exitYPos = (480 / 2) - buttonHeight - padding - buttonHeight; // Exit button position

        // Button bounding box
        startGameButtonBounds = new Rectangle(posX, startYPos, buttonWidth, buttonHeight);
        creditsButtonBounds = new Rectangle(posX, creditsYPos, buttonWidth, buttonHeight);
        exitButtonBounds = new Rectangle(posX, exitYPos, buttonWidth, buttonHeight);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(startGameButtonTexture, startGameButtonBounds.x, startGameButtonBounds.y, startGameButtonBounds.width, startGameButtonBounds.height);
        batch.draw(creditsButtonTexture, creditsButtonBounds.x, creditsButtonBounds.y, creditsButtonBounds.width, creditsButtonBounds.height);
        batch.draw(exitButtonTexture, exitButtonBounds.x, exitButtonBounds.y, exitButtonBounds.width, exitButtonBounds.height);
        batch.end();

        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if (startGameButtonBounds.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new GameScreen(game));
                dispose();
            } else if (creditsButtonBounds.contains(touchPos.x, touchPos.y)) {
                // implement credits later
            } else if (exitButtonBounds.contains(touchPos.x, touchPos.y)) {
                Gdx.app.exit();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        startGameButtonTexture.dispose();
        creditsButtonTexture.dispose();
        exitButtonTexture.dispose();
    }
}
