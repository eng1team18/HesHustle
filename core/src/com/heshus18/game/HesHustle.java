package com.heshus18.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Input;

public class HesHustle extends ApplicationAdapter {
	SpriteBatch batch;
	Texture character;
	Texture playableMap;
	OrthographicCamera camera;
	Rectangle player;
	float mapWidth, mapHeight;

	@Override
	public void create () {
		//creating camera and sprite batch
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		//rendering character and background model
		character = new Texture(Gdx.files.internal("Octodecimals.png"));
		playableMap = new Texture(Gdx.files.internal("checkerboard.png"));

		mapWidth = playableMap.getWidth();
		mapHeight = playableMap.getHeight();

		//creating player rectangle
		player = new Rectangle();
		player.x = 800 / 2 - 64 / 2;
		player.y = 20;
		player.width = 64;
		player.height = 64;

	}

	@Override
	public void render () {
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
		batch.end();

		float deltaX = 200 * Gdx.graphics.getDeltaTime();
		float deltaY = 200 * Gdx.graphics.getDeltaTime();

		// Setting the map boundaries position
		float minX = -(mapWidth / 2); // Left edge of the map
		float maxX = (mapWidth / 2) - player.width; // Right edge of the map
		float minY = -(mapHeight / 2); // Bottom edge of the map
		float maxY = (mapHeight / 2) - player.height; // Top edge of the map

		//performing character movement
		if (Gdx.input.isKeyPressed(Input.Keys.A)) player.x = Math.max(player.x - deltaX, minX);
		if (Gdx.input.isKeyPressed(Input.Keys.D)) player.x = Math.min(player.x + deltaX, maxX);
		if (Gdx.input.isKeyPressed(Input.Keys.W)) player.y = Math.min(player.y + deltaY, maxY);
		if (Gdx.input.isKeyPressed(Input.Keys.S)) player.y = Math.max(player.y - deltaY, minY);

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
	}

}
