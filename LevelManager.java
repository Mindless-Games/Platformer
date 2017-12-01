package games.mindless.bobhop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.location.*;
import android.media.Image;

import java.util.ArrayList;

public class LevelManager {

    private String level;

    int mapWidth;
    int mapHeight;

    Player player;
    int playerIndex;

    private boolean playing;
    float gravity;

    LevelData levelData;
    ArrayList<GameObject> gameObjects;
    ArrayList<Background> backgrounds;

    ArrayList<Rect> currentButtons;
    Bitmap[] bitmapArray;

    public LevelManager(Context context, int pixelsPerMeter, int screenWidth, InputController ic,
                        String level, float px, float py) {
        this.level = level;

        switch(level) {
            case "Level1":
                levelData = new Level1();
                break;
            case "Level2":
                levelData = new Level2();
                break;
            //add more levels here
        }

        //to hold all our GameObjects
        gameObjects = new ArrayList<>();

        //To hold 1 of every bitmap
        bitmapArray = new Bitmap[25];

        //load all gameObjects and Bitmaps
        loadMapData(context, pixelsPerMeter, px, py);
        loadBackgrounds(context, pixelsPerMeter, screenWidth);

        //ready to play
        //playing = true;

        setWaypoints();
    }

    public Bitmap getBitmap(char blockType) {

        int index;
        switch (blockType) {
            case '.':
                index = 0;
                break;
            case '1':
                index = 1;
                break;
            case 'p':
                index = 2;
                break;
            case 'c':
                index = 3;
                break;
            case 's':
                index = 4;
                break;
            case 'r':
                index = 5;
                break;
            case 't':
                index = 6;
                break;
            case 'g':
                index = 7;
                break;
            default:
                index = 0;
                break;
        }

        return bitmapArray[index];
    }

    public int getBitmapIndex(char blockType) {

        int index;
        switch (blockType) {
            case '.':
                index = 0;
                break;
            case '1':
                index = 1;
                break;
            case 'p':
                index = 2;
                break;
            case 'c':
                index = 3;
                break;
            case 's':
                index = 4;
                break;
            case 'r':
                index = 5;
                break;
            case 't':
                index = 6;
                break;
            case 'g':
                index = 7;
                break;
            default:
                index = 0;
                break;
        }

        return index;
    }

    private void loadBackgrounds(Context context, int pixelsPerMeter, int screenWidth) {

        backgrounds = new ArrayList<Background>();
        for(BackgroundData bgData : levelData.backgroundDataList) {
            backgrounds.add(new Background(context, pixelsPerMeter, screenWidth, bgData));
        }
    }

    public void setWaypoints() {
        // loop through all game objects looking for groundmonsters
        for(GameObject groundmonster : this.gameObjects) {
            if(groundmonster.getType() == 'g') {
                //set waypoints for this monster
                //find the tile beneath monster
                //this relies on designer putting the monster in a sensible location

                int startTileIndex = -1;
                int startMonsterIndex = 0;
                float waypointX1 = -1;
                float waypointX2 = -1;

                for (GameObject tile : this.gameObjects) {
                    startTileIndex++;
                    if(tile.getWorldLocation().y == groundmonster.getWorldLocation().y + 1) {
                        //tile is 1 space below current monster
                        //check to see if x coordinate is the same
                        if(tile.getWorldLocation().x == groundmonster.getWorldLocation().x) {
                            //found tile monster is on
                            //now go left as possible before non traversible tile is found
                            //upto a maximum of 5 tiles
                            //this maximum value can be changed

                            //left for loop
                            for(int i = 0; i < 5; i++) {
                                if(!gameObjects.get(startTileIndex - i).isTraversable()) {
                                    //set left waypoint
                                    waypointX1 = gameObjects.get(startTileIndex - (i + 1)).getWorldLocation().x;
                                    break;
                                }
                                else {
                                    //set max to 5 tiles as no non traversible tile was found
                                    waypointX1 = gameObjects.get(startTileIndex - 5).getWorldLocation().x;
                                }
                            }

                            //right for loop
                            for(int i = 0; i < 5; i++) {
                                if(!gameObjects.get(startTileIndex + i).isTraversable()) {
                                    //set right waypoint
                                    waypointX2 = gameObjects.get(startTileIndex + (i - 1)).getWorldLocation().x;
                                    break;
                                }
                                else {
                                    //set max to 5 tiles
                                    waypointX2 = gameObjects.get(startTileIndex + 5).getWorldLocation().x;
                                }
                            }

                            GroundMonster g = (GroundMonster) groundmonster;
                            g.setWaypoint(waypointX1, waypointX2);
                        }
                    }
                }
            }
        }
    }

    public boolean isPlaying() {
        return playing;
    }

    public void switchPlayingStatus() {
        playing = !playing;
        if(playing) {
            gravity = 6;
        }
        else {
            gravity = 0;
        }
    }

    private void loadMapData(Context context, int pixelsPerMeter, float px, float py) {

        char c;

        int currentIndex = -1;
        int teleportIndex = -1;

        mapHeight = levelData.tiles.size();
        mapWidth = levelData.tiles.get(0).length();

        for(int i = 0; i < levelData.tiles.size(); i++) {
            for(int j = 0; j < levelData.tiles.get(i).length(); j++) {
                c = levelData.tiles.get(i).charAt(j);

                if(c != '.') {
                    currentIndex++;
                    switch(c) {
                        case '1':
                            //add grass
                            gameObjects.add(new Grass(j, i, c));
                            break;
                        case 'p':
                            //add player
                            gameObjects.add(new Player(context, px, py, pixelsPerMeter));
                            //index of player
                            playerIndex = currentIndex;
                            //reference to player
                            player = (Player)gameObjects.get(playerIndex);

                            break;
                        case 'c':
                            //add coin
                            gameObjects.add(new Coin(j, i, c));
                            break;
                        case 's':
                            //add spike
                            gameObjects.add(new Spike(j, i, c));
                            break;
                        case 'r':
                            //add right spike
                            gameObjects.add(new SpikeRight(j, i, c));
                            break;
                        case 't':
                            //add teleporter
                            teleportIndex++;
                            gameObjects.add(new Portal(j, i, c, levelData.locations.get(teleportIndex)));
                            break;
                        case 'g':
                            //add groundmonster
                            gameObjects.add(new GroundMonster(context, j, i, c, pixelsPerMeter));
                            break;
                    }
                    //if the bitmap isnt prepared yet
                    if (bitmapArray[getBitmapIndex(c)] == null) {

                        //prepare it now
                        bitmapArray[getBitmapIndex(c)] = gameObjects.get(currentIndex).prepareBitmap(context,
                                gameObjects.get(currentIndex).getBitmapName(), pixelsPerMeter);
                    }
                }
            }
        }
    }
}
