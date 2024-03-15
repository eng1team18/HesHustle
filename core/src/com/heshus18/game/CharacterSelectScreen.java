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
    private Rectangle girlLightButtonBounds;
    private Rectangle girlDarkButtonBounds;

    private Texture girlDark, girlLight;

    public CharacterSelectScreen(HesHustle game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        girlLight = new Texture(Gdx.files.internal("GirlLightPreview.png"));
        girlDark = new Texture(Gdx.files.internal("GirlDarkPreview.png"));
        font = new BitmapFont();
        font.getData().setScale(2f);

        float buttonWidth = 384;
        float buttonHeight = 384;
        float padding = 10;
        float posXLight = 0; //GirlLight button position
        float posXDark = 800 - buttonWidth; //GirlDark button position
        float posY = 20; //Button height

        // Button bounding box
        girlLightButtonBounds = new Rectangle(posXLight, posY, buttonWidth, buttonHeight);
        girlDarkButtonBounds = new Rectangle(posXDark, posY, buttonWidth, buttonHeight);
    }

    public void render(float delta){
        ScreenUtils.clear(0.3765f, 0.4588f, 0.5882f, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        font.draw(batch, "Choose Character", 280, 450);
        batch.draw(girlLight, girlLightButtonBounds.x, girlLightButtonBounds.y,
                girlLightButtonBounds.width, girlLightButtonBounds.height);
        batch.draw(girlDark, girlDarkButtonBounds.x, girlDarkButtonBounds.y,
                girlDarkButtonBounds.width, girlDarkButtonBounds.height);
        batch.end();

        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if (girlLightButtonBounds.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new GameScreen(game, "GirlLightSpriteSheet.png"));
                dispose();
            } else if (girlDarkButtonBounds.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new GameScreen(game, "GirlDarkSpriteSheet.png"));
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
        girlDark.dispose();
        girlLight.dispose();
        font.dispose();
    }
}

