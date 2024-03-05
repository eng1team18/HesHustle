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
	Texture checkerboard;
	OrthographicCamera camera;
	Rectangle player;


	@Override
	public void create () {
		//creating camera and sprite batch
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		//rendering character and background model
		character = new Texture(Gdx.files.internal("Octodecimals.png"));
		checkerboard = new Texture(Gdx.files.internal("checkerboard.png"));

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
		camera.position.y = player.getY() +player.getHeight()/2;
		camera.update();

		//draw map and character image over player
		ScreenUtils.clear(0.7f, 0.7f, 1, 0);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(checkerboard, -(checkerboard.getWidth()/2), -(checkerboard.getHeight()/2));
		batch.draw(character, player.x, player.y);
		batch.end();

		//performing character movement
		if(Gdx.input.isKeyPressed(Input.Keys.A)) player.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.D)) player.x += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.W)) player.y += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.S)) player.y -= 200 * Gdx.graphics.getDeltaTime();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		character.dispose();
		checkerboard.dispose();
	}

}
