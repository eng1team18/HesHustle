package com.heshus18.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    final HesHustle game;

    SpriteBatch batch;
    Texture character;
    Texture playableMap;
    Array<Building> buildings;
    OrthographicCamera camera;
    Rectangle player;
    float mapWidth, mapHeight;
    EnergyBar energyBar;
    OrthographicCamera hudCamera;

    public GameScreen(final HesHustle game) {
        this.game = game;

        // Use the game's batch for drawing
        batch = game.batch;

        buildings = new Array<Building>();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        //rendering character and background model
        character = new Texture(Gdx.files.internal("Octodecimals.png"));
        playableMap = new Texture(Gdx.files.internal("checkerboard.png"));

        // Building textures
        Texture buildingTexture1 = new Texture(Gdx.files.internal("building.png"));
        Texture buildingTexture2 = new Texture(Gdx.files.internal("building2.png"));
        Texture buildingTexture3 = new Texture(Gdx.files.internal("building3.png"));

        //creating a camera for the HUDs
        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hudCamera.update();

        energyBar = new EnergyBar(100f, 250f, 35f, new Vector2(20f, Gdx.graphics.getHeight() - 45f),
                "energyBarBackground.png", "energyBarForeground.png");

        mapWidth = playableMap.getWidth();
        mapHeight = playableMap.getHeight();

        //creating player rectangle
        player = new Rectangle();
        player.x = 800 / 2 - 64 / 2;
        player.y = 20;
        player.width = 64;
        player.height = 64;

        // Adding building to the map
        // Add more building by just copy-pasting these and assigning the texture
        buildings.add(new Building(100, 100, 120, 80, buildingTexture1));
        buildings.add(new Building(30, -150, 150, 80, buildingTexture2));
        buildings.add(new Building(330, 150, 150, 80, buildingTexture3));
    }

    @Override
    public void render(float delta) {
        //move camera to player position
        camera.position.x = player.getX() + player.getWidth()/2;
        camera.position.y = player.getY() + player.getHeight()/2;
        camera.update();

        //draw map and character image over player
        ScreenUtils.clear(0.7f, 0.7f, 1, 0);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(playableMap, -(playableMap.getWidth()/2), -(playableMap.getHeight()/2));
        batch.draw(character, player.x, player.y);

        // Drawing the buildings
        for(Building building : buildings) {
            batch.draw(building.texture, building.bounds.x, building.bounds.y, building.bounds.width, building.bounds.height);
        }
        batch.end();

        //Camera for the HUDs
        hudCamera.update();
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        energyBar.render(batch);
        batch.end();

        // This is so W/S and D/A key still works when the other are colliding with objects
        float deltaX = 200 * Gdx.graphics.getDeltaTime();
        float deltaY = 200 * Gdx.graphics.getDeltaTime();
        float potentialX = player.x + (Gdx.input.isKeyPressed(Input.Keys.D) ? deltaX : 0) - (Gdx.input.isKeyPressed(Input.Keys.A) ? deltaX : 0);
        float potentialY = player.y + (Gdx.input.isKeyPressed(Input.Keys.W) ? deltaY : 0) - (Gdx.input.isKeyPressed(Input.Keys.S) ? deltaY : 0);

        // Setting the map boundaries position
        float minX = -(mapWidth / 2); // Left edge of the map
        float maxX = (mapWidth / 2) - player.width; // Right edge of the map
        float minY = -(mapHeight / 2); // Bottom edge of the map
        float maxY = (mapHeight / 2) - player.height; // Top edge of the map

        //performing character movement
        if (Gdx.input.isKeyPressed(Input.Keys.A)) potentialX -= deltaX;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) potentialX += deltaX;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) potentialY += deltaY;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) potentialY -= deltaY;

        //Remove later, this is to test add/drain energy
        if(Gdx.input.isKeyJustPressed(Input.Keys.K)) {
            energyBar.drainEnergy(10f);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            energyBar.addEnergy(10f);
        }

        potentialX = Math.max(Math.min(potentialX, maxX), minX);
        potentialY = Math.max(Math.min(potentialY, maxY), minY);

        Rectangle potentialPlayerX = new Rectangle(potentialX, player.y, player.width, player.height);
        Rectangle potentialPlayerY = new Rectangle(player.x, potentialY, player.width, player.height);

        // This is the building collision checks, check for either X, Y or both axis are colliding
        boolean collisionX = false, collisionY = false;
        for (Building building : buildings) {
            if (potentialPlayerX.overlaps(building.bounds)) {
                collisionX = true;
            }
            if (potentialPlayerY.overlaps(building.bounds)) {
                collisionY = true;
            }
            if (collisionX && collisionY) break;
        }

        // Allow character to move if no collision
        if (!collisionX) {
            player.x = Math.max(Math.min(potentialX, maxX), minX);
        }

        if (!collisionY) {
            player.y = Math.max(Math.min(potentialY, maxY), minY);
        }
    }

    @Override
    public void resize(int width, int height) {
        // Expands the camera when the game window screen is changed instead of forcing a fixed size
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void dispose () {
        batch.dispose();
        character.dispose();
        playableMap.dispose();
        energyBar.dispose();
    }

    @Override
    public void show() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
}
