package com.heshus18.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Main script that is run when the game is opened.
 * Send player to Main menu screen after opening the game.
 */
public class HesHustle extends Game {
	public SpriteBatch batch;
	public BitmapFont font;

	/**
	 * Create sprite batch and font for the game
	 */
	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	public static void main(String[] arg) {
	}
}
