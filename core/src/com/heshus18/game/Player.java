package com.heshus18.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Represents the player in the game, animating and drawing the player sprite depending on current action.
 * Creates array of map objects, and detects player collision with each
 * Detects player interactions with interactive map objects
 */

public class Player {
    //No. of rows/columns in sprite sheet, and what animation each row represents
    final int COLS, ROWS;
    final int LEFTIDLE, RIGHTIDLE, BACKIDLE, LEFTWALK, RIGHTWALK, BACKWALK;
    private int currentAnimation;
    private float stateTime;

    //Animation classes for each animation
    Animation<TextureRegion> leftIdle, rightIdle, backIdle, leftWalk, rightWalk, backWalk;

    //Array containing all frames of each animation
    TextureRegion[] leftIdleFrames, rightIdleFrames, backIdleFrames, leftWalkFrames, rightWalkFrames, backWalkFrames;

    TextureRegion currentFrame;
    Animation[] animations;

    //Movement boolean variables
    boolean leftMove, rightMove, upMove, downMove;
    //player hit box
    Rectangle player;
    float interactSize, speed;


    //Map variables
    boolean collisionUp, collisionDown, collisionLeft, collisionRight;
    TiledMap map;
    MapLayer buildingsAndBounds;
    MapObjects buildingsAndBoundsObjects;
    String currentBuilding;
    float scaley;

    /**
     * Creates an instance of player.
     * Creates all animations for player - 3 idle and 3 walk based on direction.
     * Imports map and creates array of map objects
     * @param spriteSheet The sheet with all player animations
     */
    public Player(Texture spriteSheet) {
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
        System.arraycopy(tmp[LEFTIDLE], 0, leftIdleFrames, 0, COLS);
        //Create instance of Animation using the array of animation frames, with a frame period of 150ms
        leftIdle = new Animation(0.15f, (Object[]) leftIdleFrames);
        leftIdle.setPlayMode(Animation.PlayMode.LOOP);

        //Repeat for all animations
        //rightIdle animation
        rightIdleFrames = new TextureRegion[COLS];
        System.arraycopy(tmp[RIGHTIDLE], 0, rightIdleFrames, 0, COLS);
        rightIdle = new Animation(0.15f, (Object[]) rightIdleFrames);
        rightIdle.setPlayMode(Animation.PlayMode.LOOP);

        //backIdle animation
        backIdleFrames = new TextureRegion[COLS];
        System.arraycopy(tmp[BACKIDLE], 0, backIdleFrames, 0, COLS);
        backIdle = new Animation(0.15f, (Object[]) backIdleFrames);
        backIdle.setPlayMode(Animation.PlayMode.LOOP);

        //leftWalk animation
        leftWalkFrames = new TextureRegion[COLS];
        System.arraycopy(tmp[LEFTWALK], 0, leftWalkFrames, 0, COLS);
        leftWalk = new Animation(0.15f, (Object[]) leftWalkFrames);
        leftWalk.setPlayMode(Animation.PlayMode.LOOP);

        //rightWalk animation
        rightWalkFrames = new TextureRegion[COLS];
        System.arraycopy(tmp[RIGHTWALK], 0, rightWalkFrames, 0, COLS);
        rightWalk = new Animation(0.15f, (Object[]) rightWalkFrames);
        rightWalk.setPlayMode(Animation.PlayMode.LOOP);

        //backWalk animation
        backWalkFrames = new TextureRegion[COLS];
        System.arraycopy(tmp[BACKWALK], 0, backWalkFrames, 0, COLS);
        backWalk = new Animation(0.15f, (Object[]) backWalkFrames);
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

        //initialise movement variables
        leftMove = false;
        rightMove = false;
        upMove = false;
        downMove = false;

        //create player hit box
        player = new Rectangle();
        player.x = 1600;
        player.y = 1100;
        player.width = 31;
        player.height = 88;

        //Player variables
        interactSize = 20;
        speed = 300;

        //Collision variables
        collisionUp = false;
        collisionDown = false;
        collisionLeft = false;
        collisionRight = false;

        //Building the map layers/objects
        map = GameScreen.background;
        buildingsAndBounds = map.getLayers().get("Object Layer 1");
        buildingsAndBoundsObjects = buildingsAndBounds.getObjects();
    }

    /**
     * Set the current player animation
     *
     * @param currentAnimation The animation to be set
     */
    public void setCurrentAnimation(int currentAnimation) {
        this.currentAnimation = currentAnimation;
        stateTime = 0;
    }

    public int getCurrentAnimation() {
        return currentAnimation;
    }

    public float getX() {
        return player.getX();
    }

    public float getY() {
        return player.getY();
    }

    public float getWidth() {
        return player.getWidth();
    }

    public float getHeight() {
        return player.getHeight();
    }

    /**
     * Updates the sprite batch with a drawing of the current animation frame
     *
     * @param batch   The sprite being used to render GameScreen
     * @param playerX the player's x coordinate
     * @param playerY the player's y coordinate
     */
    public void update(SpriteBatch batch, float playerX, float playerY) {
        //Time since last animation frame
        stateTime += Gdx.graphics.getDeltaTime();

        //Receive current animation frame from animation
        currentFrame = (TextureRegion) animations[currentAnimation].getKeyFrame(stateTime, true);

        //Draw current animation frame
        batch.begin();
        batch.draw(currentFrame, playerX - 47, playerY, 128, 128);
        batch.end();
    }

    /**
     * Allows the player to move around the map
     * Detects collision with map objects and does not allow player to pass through these.
     * Detects interactive map objects and allows player to interact with them.
     * @param popUpManager The instance of popUpManager being used in the game.
     */
    public void move(PopUpManager popUpManager) {
        //Get which direction the player is moving and set animation accordingly
        //Up move
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            if (this.getCurrentAnimation() != BACKWALK)
                this.setCurrentAnimation(BACKWALK);
            upMove = true;
        } else upMove = false;
        //Left move
        //If also moving up, don't overwrite up animation
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (this.getCurrentAnimation() != LEFTWALK && !upMove)
                this.setCurrentAnimation(LEFTWALK);
            leftMove = true;
        } else leftMove = false;
        //Right move
        //If also moving up, don't overwrite up animation
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (this.getCurrentAnimation() != RIGHTWALK && !upMove)
                this.setCurrentAnimation(RIGHTWALK);
            rightMove = true;
        } else rightMove = false;
        //Down move
        //If current animation is leftWalk or rightWalk, use that animation, else switch to leftWalk
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            if (this.getCurrentAnimation() != LEFTWALK &&
                    this.getCurrentAnimation() != RIGHTWALK)
                this.setCurrentAnimation(LEFTWALK);
            downMove = true;
        } else downMove = false;

        //Set idle animations based off previous direction
        if (!leftMove && !rightMove && !upMove && !downMove) {
            if (this.getCurrentAnimation() == LEFTWALK)
                this.setCurrentAnimation(LEFTIDLE);
            else if (this.getCurrentAnimation() == RIGHTWALK)
                this.setCurrentAnimation(RIGHTIDLE);
            else if (this.getCurrentAnimation() == BACKWALK)
                this.setCurrentAnimation(BACKIDLE);
        }

        //Create player interact box that is interactSize larger than the player
        //Create 4 1 dimensional player collision boxes on each side of the player with a small gap between them to
        //avoid overlap.
        scaley = GameScreen.unitScale;
        Rectangle playerHitBox = new Rectangle((player.x / scaley) - (interactSize / 2), (player.y / scaley) - (interactSize / 2),
                player.width / scaley + interactSize, player.height /scaley + interactSize);
        Rectangle upCollision = new Rectangle(player.x / scaley, player.y / scaley + player.getHeight() /scaley + 3,
                player.getWidth() /scaley, 0);
        Rectangle leftCollision = new Rectangle(player.x / scaley - 3, player.y /scaley, 0,
                player.getHeight() / scaley);
        Rectangle rightCollision = new Rectangle(player.x / scaley + player.getWidth() / scaley + 3, player.y /scaley,
                0, player.getHeight() / scaley);
        Rectangle downCollision = new Rectangle(player.x / scaley, player.y /scaley - 3,
                player.getWidth() / scaley, 0);

        //Set collision to false
        collisionUp = false;
        collisionLeft = false;
        collisionDown = false;
        collisionRight = false;

        //Iterate through array of map objects
        for (MapObject building : buildingsAndBoundsObjects) {
            //Create collision rectangle for current object
            Rectangle collisionBox = ((RectangleMapObject) building).getRectangle();
            String popUpCurrent = "";

            //Detect if player collision boxes overlap current map object, and set corresponding flags.
            if((upCollision).overlaps(collisionBox)) collisionUp = true;
            if((leftCollision).overlaps(collisionBox)) collisionLeft = true;
            if((rightCollision).overlaps(collisionBox)) collisionRight = true;
            if((downCollision).overlaps(collisionBox)) collisionDown = true;

            //Check if player interact box overlaps interactive buildings, show interact prompt,
            // and set popUpCurrent accordingly
            if (playerHitBox.overlaps(collisionBox)) {
                if (building.getName().equals("CS Building")) {
                    GameScreen.interact();
                    popUpCurrent = "studyPopUp";
                }
                if (building.getName().equals("Piazza")) {
                    GameScreen.interact();
                    popUpCurrent = "eatingPopUp";
                }
                if (building.getName().equals("Water1") || building.getName().equals("Water2") ||
                        building.getName().equals("Water3") || building.getName().equals("Water4")
                        || building.getName().equals("Water5") || building.getName().equals("Water6")) {
                    GameScreen.interact();
                    popUpCurrent = "activityPopUp";
                }
                if (building.getName().equals("Goodricke Hub")) {
                    GameScreen.interact();
                    popUpCurrent = "sleepingPopUp";
                }
                //Allow player to interact with object using E key and show corresponding pop-up
                if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                    if (!popUpCurrent.isEmpty()) {
                        //Set animation to idle if player chooses to interact
                        if (this.getCurrentAnimation() == LEFTWALK)
                            this.setCurrentAnimation(LEFTIDLE);
                        else if (this.getCurrentAnimation() == RIGHTWALK)
                            this.setCurrentAnimation(RIGHTIDLE);
                        else if (this.getCurrentAnimation() == BACKWALK)
                            this.setCurrentAnimation(BACKIDLE);

                        popUpManager.showPopUp(popUpCurrent);
                    }
                }
            }
        }

        //Allow character to move if no collision in corresponding directions
        if (rightMove && !collisionRight) player.x += Gdx.graphics.getDeltaTime() * speed;
        if (upMove && !collisionUp) player.y += Gdx.graphics.getDeltaTime() * speed;
        if (leftMove && !collisionLeft) player.x -= Gdx.graphics.getDeltaTime() * speed;
        if (downMove && !collisionDown) player.y -= Gdx.graphics.getDeltaTime() * speed;
    }
}
