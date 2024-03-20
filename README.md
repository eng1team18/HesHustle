## Heslington Hustle - ENG1 Group 18

### Creating an executable .jar game
To create a running .jar file of the game, run the 
`./gradlew desktop:dist` command inside of the game Terminal. You should find the output located at `desktop/build/libs/`

### PopUp
PopUps are shown when you interact with a building to perform various tasks or as indicator when tasks are completed.

Below is an explanation of how you can create your own PopUps.
There are 3 kinds of PopUps
- **prompt**: A pop up that will include both the "Confirm" and "Deny" buttons
- **warning**: A pop up with only a "Close" button
- **info**: A pop up with no button and will auto-close

**Creating a PopUp:**

```PopUp(String id, String message, float x, float y, float width, float height, OrthographicCamera camera, String type)```

**Example of a PopUp:**

```PopUp popUpID1 = new PopUp("popUpID1", "This is a PopUp!", 0, 0, 800, 480, hudCamera, "info");```
```popUpManager.addPopUp(popUpID1);```

For each PopUp you must make sure they have a unique ID in order to show them on screen.

**Assigning functions to a PopUp "Confirm" button**

If you would like the Confirm button to execute functions, you add the following line of code before the `popUpManager.addPopUp()` function. Here's an example.
```
studyPopUp.addConfirmAction(new Runnable() {
            @Override
            public void run() {
                if (!energyBar.drainEnergy(20f)) {
                    popUpManager.showPopUp("noEnergyPopUp");
                } else {
                    gameTime.addTime(120);
                    score.incrementTimeStudy();
                    popUpManager.showPopUp("studySuccess");
                }
            }
        });
```

The following code snippet does the following: it first adds the Confirm action to the Confirm button. It then checks if the player's energy has been drained; if they have, the score gets incremented, and an info PopUp is shown. Otherwise, a warning PopUp is shown instead.

### Map & Assets

**Assets:**
All the game's pixellated graphics were created using the website Pixilart https://www.pixilart.com/.
If you want to alter the game's graphics, under assets we left an Art folder containing the .pixil files for each of the game's sprites & sprite sheets. These can be imported into Pixilart so you can make any changes.

**Map:**
Our map was implemented using a LibGDX tilemap, more information on these can be found here: https://libgdx.com/wiki/graphics/2d/tile-maps

This allows you to use an application called Tiled (https://www.mapeditor.org/) to create and edit the visuals of the map.

Tiled also allows you to add objects, which can be used to draw hitboxes onto the map, rather than manually programming them in code. Currently, only rectangular collisions are implemented, adding any rectangular object to Object Layer 1 will automatically mean the player can't walk through that rectangle.

Our tilemap is currently set up with the following layers:
- **Object Layer 1**: Contains all of the game's objects, this includes all buildings, NPCs and trees, these cause collisions. Some mismatch their object shape intentionally to prevent the player from leaving the intended part of the map.
- **NPC Layer**: Contains the animated NPC sprites on the map, may be deleted if assessment 2 requires implementing moving/interactable NPCs.
- **Tree Layer 2**: Contains some of the tree sprites, this extra layer allowed us to have overlapping trees
- **Tree Layer 1**: Contains most of the tree sprites
- **Floor+Building Layer**: Contains all the floor and building tiles
