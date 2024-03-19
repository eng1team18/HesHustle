package com.heshus18.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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
 * Represents the player in the game, animating and drawing the player sprite depending on current action
 */

public class Player {
    //No. of rows/columns in sprite sheet, and what animation each row represents
    final int COLS, ROWS;
    final int LEFTIDLE, RIGHTIDLE, BACKIDLE, LEFTWALK, RIGHTWALK, BACKWALK;
    private int currentAnimation;
    private float stateTime;

    //Animation classes for each animation
    Animation leftIdle;
    Animation<TextureRegion> rightIdle;
    Animation<TextureRegion> backIdle;
    Animation<TextureRegion> leftWalk;
    Animation<TextureRegion> rightWalk;
    Animation<TextureRegion> backWalk;

    //Array containing all frames of each animation
    TextureRegion [] leftIdleFrames, rightIdleFrames, backIdleFrames, leftWalkFrames, rightWalkFrames, backWalkFrames;

    TextureRegion currentFrame;
    Animation [] animations;

    //Movement boolean variables
    boolean leftMove, rightMove, upMove, downMove;
    //player hit box
    Rectangle player;

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
        System.arraycopy(tmp[LEFTIDLE], 0, leftIdleFrames, 0, COLS);
        //Create instance of Animation using the array of animation frames, with a frame period of 150ms
        leftIdle = new Animation(0.15f, (Object[])leftIdleFrames);
        leftIdle.setPlayMode(Animation.PlayMode.LOOP);

        //Repeat for all animations
        //rightIdle animation
        rightIdleFrames = new TextureRegion[COLS];
        System.arraycopy(tmp[RIGHTIDLE], 0, rightIdleFrames, 0, COLS);
        rightIdle = new Animation(0.15f, (Object[])rightIdleFrames);
        rightIdle.setPlayMode(Animation.PlayMode.LOOP);

        //backIdle animation
        backIdleFrames = new TextureRegion[COLS];
        System.arraycopy(tmp[BACKIDLE], 0, backIdleFrames, 0, COLS);
        backIdle = new Animation(0.15f, (Object[])backIdleFrames);
        backIdle.setPlayMode(Animation.PlayMode.LOOP);

        //leftWalk animation
        leftWalkFrames = new TextureRegion[COLS];
        System.arraycopy(tmp[LEFTWALK], 0, leftWalkFrames, 0, COLS);
        leftWalk = new Animation(0.15f, (Object[])leftWalkFrames);
        leftWalk.setPlayMode(Animation.PlayMode.LOOP);

        //rightWalk animation
        rightWalkFrames = new TextureRegion[COLS];
        System.arraycopy(tmp[RIGHTWALK], 0, rightWalkFrames, 0, COLS);
        rightWalk = new Animation(0.15f, (Object[])rightWalkFrames);
        rightWalk.setPlayMode(Animation.PlayMode.LOOP);

        //backWalk animation
        backWalkFrames = new TextureRegion[COLS];
        System.arraycopy(tmp[BACKWALK], 0, backWalkFrames, 0, COLS);
        backWalk = new Animation(0.15f, (Object[])backWalkFrames);
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
    public int getCurrentAnimation() {return currentAnimation;}

    /**
     *
     * @return player's x coordinate
     */
    public float getX() {return player.getX();}
    public float getY() {return player.getY();}
    public float getWidth() {return player.getWidth();}
    public float getHeight() {return player.getHeight();}

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

    public void move(PopUpManager popUpManager){
        float deltaX = 200 * Gdx.graphics.getDeltaTime();
        float deltaY = 200 * Gdx.graphics.getDeltaTime();

        //an array representing potential [potentialX, potentialY]
        float potentialX = player.x + (Gdx.input.isKeyPressed(Input.Keys.D) ? deltaX : 0) - (Gdx.input.isKeyPressed(Input.Keys.A) ? deltaX : 0);
        float potentialY = player.y + (Gdx.input.isKeyPressed(Input.Keys.W) ? deltaY : 0) - (Gdx.input.isKeyPressed(Input.Keys.S) ? deltaY : 0);

        //Performing character movement and changing current animation to reflect direction
        //Up move
        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            if (this.getCurrentAnimation() != BACKWALK)
                this.setCurrentAnimation(BACKWALK);
            upMove = true;
            potentialY += deltaY;
        } else upMove = false;
        //Left move
        //If also moving up, don't overwrite up animation
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (this.getCurrentAnimation() != LEFTWALK && !upMove)
                this.setCurrentAnimation(LEFTWALK);
            leftMove = true;
            potentialX -= deltaX;
        } else leftMove = false;
        //Right move
        //If also moving up, don't overwrite up animation
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            if (this.getCurrentAnimation() != RIGHTWALK && !upMove)
                this.setCurrentAnimation(RIGHTWALK);
            rightMove = true;
            potentialX += deltaX;
        } else rightMove = false;
        //Down move
        //If current animation is leftWalk or rightWalk, use that animation, else switch to leftWalk
        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            if(this.getCurrentAnimation() != LEFTWALK &&
                    this.getCurrentAnimation() != RIGHTWALK )
                this.setCurrentAnimation(LEFTWALK);
            downMove = true;
            potentialY -= deltaY;
        } else downMove = false;

        //Set idle animations based off previous direction
        if (!leftMove && !rightMove && !upMove && !downMove){
            if(this.getCurrentAnimation() == LEFTWALK)
                this.setCurrentAnimation(LEFTIDLE);
            else if(this.getCurrentAnimation() == RIGHTWALK)
                this.setCurrentAnimation(RIGHTIDLE);
            else if(this.getCurrentAnimation() == BACKWALK)
                this.setCurrentAnimation(BACKIDLE);
        }

        float scaley = GameScreen.unitScale;
        float interactSize = 5;
        Rectangle playerHitbox = new Rectangle((player.x - interactSize) / 2, (player.y - interactSize) / 2,
                player.width + interactSize, player.height + interactSize);
        Rectangle potentialPlayerXScaled = new Rectangle(potentialX / scaley, player.y / scaley,
                player.width / scaley, player.height / scaley);
        Rectangle potentialPlayerYScaled = new Rectangle(player.x / scaley, potentialY / scaley,
                player.width / scaley, player.height / scaley);

            // This is the building collision checks, check for either X, Y or both axis are colliding
        boolean collisionX = false, collisionY = false;
        TiledMap map = GameScreen.background;
        MapLayer buildingsAndBounds = map.getLayers().get("Object Layer 1");
        MapObjects buildingsAndBoundsObjects = buildingsAndBounds.getObjects();
        String[] objNames = {"Wall1", "Wall2", "Wall3", "Wall4", "Goodricke Hub", "CS Building", "Ron Cooke",
                "Water1", "Water2", "Water3", "Water4", "Water5", "Water6", "Piazza", "Glasshouse", "Building1",
                "Building2", "Building3", "Building4", "Building5a", "Building5b", "Building5c", "Building6",
                "Building7", "Building8a", "Building8b", "Building9a", "Building9b", "Building10", "Building11",
                "Building12", "Building13", "Building14", "Building15", "Building16", "Building17", "Building18"};
        for(String i : objNames){
            MapObject current = buildingsAndBoundsObjects.get(i);
            RectangleMapObject rectangleMapObject = (RectangleMapObject) current;
            Rectangle rectangle = rectangleMapObject.getRectangle();

            if((potentialPlayerXScaled).overlaps(rectangle)){
                collisionX = true;
            }
            if(potentialPlayerYScaled.overlaps(rectangle)){
                collisionY = true;
            }
            if (collisionX && collisionY) break;

            if(playerHitbox.overlaps(rectangle)){
                if(Gdx.input.isKeyJustPressed(Input.Keys.F)){
                    if(i.equals("CS Building")) {popUpManager.showPopUp("studyPopUp");}
                    if(i.equals("Piazza")) {popUpManager.showPopUp("eatingPopUp");}
                    if(i.equals("Water1") || i.equals("Water2") || i.equals("Water3") || i.equals("Water4") ||
                            i.equals("Water5") || i.equals("Water6")) {popUpManager.showPopUp("activityPopUp");}
                    if(i.equals("Goodricke Hub")) {popUpManager.showPopUp("sleepingPopUp");}
                }
            }
        }

            // Allow character to move if no collision
            if (!collisionX) {
                player.x = potentialX;
            }

            if (!collisionY) {
                player.y = potentialY;
            }
        }
}
