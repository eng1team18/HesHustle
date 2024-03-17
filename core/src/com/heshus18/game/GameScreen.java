package com.heshus18.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.maps.tiled.*;

public class GameScreen implements Screen {
    final HesHustle game;
    SpriteBatch batch;
    Player player;
    Texture spriteSheet, playableMap;
    Array<Building> buildings;
    OrthographicCamera camera;
    public static TiledMap background;
    public static float unitScale;

    float mapWidth, mapHeight;
    EnergyBar energyBar;
    OrthographicCamera hudCamera;
    private Time gameTime;
    private Clock clockHUD;
    private OrthogonalTiledMapRenderer renderer;
    PopUpManager popUpManager;
    Score score = Score.getInstance();

    public GameScreen(final HesHustle game, String spriteChoice) {
        this.game = game;

        // Use the game's batch for drawing
        batch = game.batch;

        buildings = new Array<Building>();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        //rendering character and background model
        spriteSheet = new Texture(Gdx.files.internal(spriteChoice));
        player = new Player(spriteSheet);
        playableMap = new Texture(Gdx.files.internal("checkerboard.png"));
        background = new TmxMapLoader().load("tileMap.tmx");
        unitScale = 2f; //change this value for size?
        renderer = new OrthogonalTiledMapRenderer(background, unitScale);


        // Building textures
        Texture buildingTexture1 = new Texture(Gdx.files.internal("building.png"));
        Texture buildingTexture2 = new Texture(Gdx.files.internal("building2.png"));
        Texture buildingTexture3 = new Texture(Gdx.files.internal("building3.png"));

        //creating a camera for the HUDs
        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, 800, 480);
        hudCamera.update();

        energyBar = new EnergyBar(100f, 250f, 35f, new Vector2(20f, 480 - 45f),
                "energyBarBackground.png", "energyBarForeground.png");

        gameTime = new Time();
        clockHUD = new Clock(new Vector2(800 - 100, 480 - 20f), gameTime);

        mapWidth = playableMap.getWidth();
        mapHeight = playableMap.getHeight();

        // Adding building to the map
        // Add more building by just copy-pasting these and assigning the texture
        buildings.add(new Building(100, 100, 120, 80, buildingTexture1));
        buildings.add(new Building(30, -150, 150, 80, buildingTexture2));
        buildings.add(new Building(330, 150, 150, 80, buildingTexture3));

        popUpManager = new PopUpManager();

        PopUp studyPopUp = new PopUp("studyPopUp", "Are you sure you want to start studying?\n\nEnergy drain: 20%\nDuration: 2 Hours", 200, 170, 400, 170, hudCamera, "prompt");
        studyPopUp.addConfirmAction(() -> {
            if (!energyBar.drainEnergy(20f)) {
                popUpManager.showPopUp("noEnergyPopUp");
            }
            else {
                gameTime.addTime(120);
                score.incrementTimeStudy();
                popUpManager.showPopUp("studySucess");
            }
        });
        popUpManager.addPopUp(studyPopUp);

        PopUp eatingPopUp = new PopUp("eatingPopUp", "Are you sure you want to start eating?\n\nEnergy drain: 10%\nDuration: 1 Hour", 200, 170, 400, 170, hudCamera, "prompt");
        eatingPopUp.addConfirmAction(() -> {
            if (!energyBar.drainEnergy(10f)) {
                popUpManager.showPopUp("noEnergyPopUp");
            }
            else {
                gameTime.addTime(60);
                score.incrementTimeAte();
                popUpManager.showPopUp("eatingSucess");
            }
        });
        popUpManager.addPopUp(eatingPopUp);

        PopUp activityPopUp = new PopUp("activityPopUp", "Are you sure you want to start this activity?\n\nEnergy drain: 15%\nDuration: 2 Hours", 200, 170, 400, 170, hudCamera, "prompt");
        activityPopUp.addConfirmAction(() -> {
            if (!energyBar.drainEnergy(15f)) {
                popUpManager.showPopUp("noEnergyPopUp");
            }
            else {
                gameTime.addTime(120);
                score.incrementTimeActivity();
                popUpManager.showPopUp("activitySucess");
            }
        });
        popUpManager.addPopUp(activityPopUp);

        PopUp sleepingPopUp = new PopUp("sleepingPopUp", "Are you sure you want to go to bed?\n\nYou will advance to the next day.", 200, 170, 400, 170, hudCamera, "prompt");
        sleepingPopUp.addConfirmAction(() -> {
            int currentHour = gameTime.getHour();
            int currentDay = gameTime.getDay();

            if (currentHour < 18) {
                popUpManager.showPopUp("cantSleepPopUp");
            } else {
                if (currentDay == 7) {
                    score.incrementTimeSlept();
                    game.setScreen(new GameOverScreen(game));
                } else {
                    gameTime.nextDay();
                    score.incrementTimeSlept();
                    if (currentHour >= 18 && currentHour <= 22) {
                        energyBar.addEnergy(100f);
                    } else {
                        energyBar.addEnergy(50f);
                    }
                    String newMessage = "You went to bed! It's now " + gameTime.getDayName(gameTime.getDay()) + ".";
                    popUpManager.updateMessage("nextDay", newMessage);
                    popUpManager.showPopUp("nextDay");
                }
            }
        });
        popUpManager.addPopUp(sleepingPopUp);


        PopUp noEnergyPopUp = new PopUp("noEnergyPopUp", "Uh oh! You don't have enough energy!\n\nTime to head to bed.", 200, 170, 400, 170, hudCamera, "warning");
        popUpManager.addPopUp(noEnergyPopUp);

        PopUp cantSleepPopUp = new PopUp("cantSleepPopUp", "It is too early to go to bed!\n\nCome back later.", 200, 170, 400, 170, hudCamera, "warning");
        popUpManager.addPopUp(cantSleepPopUp);

        PopUp eatingSucess = new PopUp("eatingSucess", "You've finished eating!", 0, 0, 800, 480, hudCamera, "info");
        popUpManager.addPopUp(eatingSucess);

        PopUp studySucess = new PopUp("studySucess", "You've finished studying!", 0, 0, 800, 480, hudCamera, "info");
        popUpManager.addPopUp(studySucess);

        PopUp activitySucess = new PopUp("activitySucess", "You've finished a recreational activity!", 0, 0, 800, 480, hudCamera, "info");
        popUpManager.addPopUp(activitySucess);

        String message = "You went to bed! It's now " + gameTime.getDayName(gameTime.getDay()) + ".";
        PopUp nextDay = new PopUp("nextDay", message, 0, 0, 800, 480, hudCamera, "info");
        popUpManager.addPopUp(nextDay);

    }

    @Override
    public void render(float delta) {
        //move camera to player position
        camera.position.x = player.getX() + player.getWidth()/2;
        camera.position.y = player.getY() + player.getHeight()/2;
        camera.update();

        //draw map image
        ScreenUtils.clear(0.3765f, 0.4588f, 0.5882f, 1);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        //batch.draw(playableMap, -(playableMap.getWidth()/2), -(playableMap.getHeight()/2));

        // Drawing the buildings
        for(Building building : buildings) {
            batch.draw(building.texture, building.bounds.x, building.bounds.y, building.bounds.width, building.bounds.height);
        }
        batch.end();
        renderer.setView(camera);
        renderer.render();

        //draw current player model
        player.update(batch, player.getX(), player.getY());

        //Camera for the HUDs
        hudCamera.update();
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        energyBar.render(batch);
        clockHUD.render(batch);
        popUpManager.update();
        popUpManager.render(batch);
        batch.end();

        if (!popUpManager.isAnyPopUpVisible()) {player.move(buildings, mapWidth, mapHeight);}

            //Remove later, keybindings for testing only
            if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
                gameTime.nextDay();
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
                popUpManager.showPopUp("studyPopUp");
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
                popUpManager.showPopUp("eatingPopUp");
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
                popUpManager.showPopUp("activityPopUp");
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.J)) {
                popUpManager.showPopUp("sleepingPopUp");
            }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            popUpManager.declineVisiblePopUp();
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
        spriteSheet.dispose();
        playableMap.dispose();
        energyBar.dispose();
        clockHUD.dispose();
        popUpManager.dispose();
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
