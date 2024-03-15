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

public class CharacterSelectScreen implements Screen {
    final HesHustle game;
    OrthographicCamera camera;
    SpriteBatch batch;
    BitmapFont font;
    private Rectangle kenzieButtonBounds;
    private Rectangle isabelleButtonBounds;

    private Texture kenzie, isabelle;

    public CharacterSelectScreen(HesHustle game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        kenzie = new Texture(Gdx.files.internal("KenziePreview.png"));
        isabelle = new Texture(Gdx.files.internal("IsabellePreview.png"));
        font = new BitmapFont();
        font.getData().setScale(2f);

        float buttonWidth = 384;
        float buttonHeight = 384;
        float padding = 10;
        float posXLight = 0; //Kenzie button position
        float posXDark = 800 - buttonWidth; //Isabelle button position
        float posY = 20; //Button height

        // Button bounding box
        kenzieButtonBounds = new Rectangle(posXLight, posY, buttonWidth, buttonHeight);
        isabelleButtonBounds = new Rectangle(posXDark, posY, buttonWidth, buttonHeight);
    }

    public void render(float delta){
        ScreenUtils.clear(0.3765f, 0.4588f, 0.5882f, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        font.draw(batch, "Choose Character", 280, 450);
        batch.draw(kenzie, kenzieButtonBounds.x, kenzieButtonBounds.y,
                kenzieButtonBounds.width, kenzieButtonBounds.height);
        batch.draw(isabelle, isabelleButtonBounds.x, isabelleButtonBounds.y,
                isabelleButtonBounds.width, isabelleButtonBounds.height);
        batch.end();

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
    @Override
    public void show() {}

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
        isabelle.dispose();
        kenzie.dispose();
        font.dispose();
    }
}

