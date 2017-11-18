package games.mindless.bobhop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
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

    ArrayList<Rect> currentButtons;
    Bitmap[] bitmapArray;

    public LevelManager(Context context, int pixelsPerMeter, int screenWidth, InputController ic,
                        String level, float px, float py) {
        this.level = level;

        switch(level) {
            case "Level1":
                levelData = new Level1();
                break;
            //add more levels here
        }

        //to hold all our GameObjects
        gameObjects = new ArrayList<>();

        //To hold 1 of every bitmap
        bitmapArray = new Bitmap[25];

        //load all gameObjects and Bitmaps
        loadMapData(context, pixelsPerMeter, px, py);

        //ready to play
        //playing = true;
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
            default:
                index = 0;
                break;
        }

        return index;
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
                            gameObjects.add(new Coin(j, i, c));
                            break;
                        case 's':
                            gameObjects.add(new Spike(j, i, c));
                            break;
                        case 'r':
                            gameObjects.add(new SpikeRight(j, i, c));
                            break;
                        case 't':
                            gameObjects.add(new Portal(j, i, c));
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
