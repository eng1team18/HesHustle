package com.heshus18.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Represents the player in the game, animating and drawing the player sprite depending on current action
 */

public class Player {
    //No. of rows/columns in sprite sheet, and what animation each row represents
    final int COLS;
    final int ROWS;
    final int LEFTIDLE;
    final int RIGHTIDLE;
    final int BACKIDLE;
    final int LEFTWALK;
    final int RIGHTWALK;
    final int BACKWALK;

    private int currentAnimation;
    private float stateTime;

    //Animation classes for each animation
    Animation<TextureRegion> leftIdle;
    Animation<TextureRegion> rightIdle;
    Animation<TextureRegion> backIdle;
    Animation<TextureRegion> leftWalk;
    Animation<TextureRegion> rightWalk;
    Animation<TextureRegion> backWalk;

    //Array containing all frames of each animation
    TextureRegion [] leftIdleFrames;
    TextureRegion [] rightIdleFrames;
    TextureRegion [] backIdleFrames;
    TextureRegion [] leftWalkFrames;
    TextureRegion [] rightWalkFrames;
    TextureRegion [] backWalkFrames;

    TextureRegion currentFrame;
    Animation [] animations;

    /**
     * Creates an instance of player.
     * Creates all animations for player - 3 idle and 3 walk based on direction.
     *
     * @param spriteSheet The sheet with all player animations
     */
    public Player(Texture spriteSheet){
        //Define number of items in spriteSheet, and assign integer values to each separate animation
        COLS = 4;
        ROWS = 6;
        LEFTIDLE = 0;
        RIGHTIDLE = 1;
        BACKIDLE = 2;
        LEFTWALK = 3;
        RIGHTWALK = 4;
        BACKWALK = 5;

        //Create 2D array that splits spriteSheet into all separate images
        TextureRegion[][] tmp = TextureRegion.split(spriteSheet,
                spriteSheet.getWidth() / COLS,
                spriteSheet.getHeight() / ROWS);

        //leftIdle animation
        //For row LEFTIDLE of spriteSheet, create 1D array containing all images (frames) in that row
        leftIdleFrames = new TextureRegion[COLS];
        for(int i=0; i < COLS; i++) {
            leftIdleFrames[i] = tmp[LEFTIDLE][i];
        }
        //Create instance of Animation using the array of animation frames, with a frame period of 150ms
        leftIdle = new Animation(0.15f, leftIdleFrames);
        leftIdle.setPlayMode(Animation.PlayMode.LOOP);

        //Repeat for all animations
        //rightIdle animation
        rightIdleFrames = new TextureRegion[COLS];
        for(int i=0; i < COLS; i++) {
            rightIdleFrames[i] = tmp[RIGHTIDLE][i];
        }
        rightIdle = new Animation(0.15f, rightIdleFrames);
        rightIdle.setPlayMode(Animation.PlayMode.LOOP);

        //backIdle animation
        backIdleFrames = new TextureRegion[COLS];
        for(int i=0; i < COLS; i++) {
                backIdleFrames[i] = tmp[BACKIDLE][i];
        }
        backIdle = new Animation(0.15f, backIdleFrames);
        backIdle.setPlayMode(Animation.PlayMode.LOOP);

        //leftWalk animation
        leftWalkFrames = new TextureRegion[COLS];
        for(int i=0; i < COLS; i++) {
            leftWalkFrames[i] = tmp[LEFTWALK][i];
        }
        leftWalk = new Animation(0.15f, leftWalkFrames);
        leftWalk.setPlayMode(Animation.PlayMode.LOOP);

        //rightWalk animation
        rightWalkFrames = new TextureRegion[COLS];
        for(int i=0; i < COLS; i++) {
            rightWalkFrames[i] = tmp[RIGHTWALK][i];
        }
        rightWalk = new Animation(0.15f, rightWalkFrames);
        rightWalk.setPlayMode(Animation.PlayMode.LOOP);

        //backWalk animation
        backWalkFrames = new TextureRegion[COLS];
        for(int i=0; i < COLS; i++) {
            backWalkFrames[i] = tmp[BACKWALK][i];
        }
        backWalk = new Animation(0.15f, backWalkFrames);
        backWalk.setPlayMode(Animation.PlayMode.LOOP);

        //Put animations in animation array
        animations = new Animation[ROWS];
        animations[LEFTIDLE] = leftIdle;
        animations[RIGHTIDLE] = rightIdle;
        animations[BACKIDLE] = backIdle;
        animations[LEFTWALK] = leftWalk;
        animations[RIGHTWALK] = rightWalk;
        animations[BACKWALK] = backWalk;

        //Initialise starting animation
        setCurrentAnimation(LEFTIDLE);
    }

    /**
     * Set the current player animation
     * @param currentAnimation The animation to be set
     */
    public void setCurrentAnimation(int currentAnimation){
        this.currentAnimation = currentAnimation;
        stateTime = 0;
    }

    /**
     *
     * @return current player animation
     */
    public int getCurrentAnimation(){
        return currentAnimation;
    }

    /**
     * Updates the sprite batch with a drawing of the current animation frame
     * @param batch The sprite being used to render GameScreen
     * @param playerX the player's x coordinate
     * @param playerY the player's y coordinate
     */
    public void update(SpriteBatch batch, float playerX, float playerY){
        stateTime += Gdx.graphics.getDeltaTime();

        currentFrame = (TextureRegion) animations[currentAnimation].getKeyFrame(stateTime, true);

        batch.begin();
        batch.draw(currentFrame, playerX -47, playerY, 128, 128);
        batch.end();
    }
}