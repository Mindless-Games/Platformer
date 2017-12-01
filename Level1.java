package games.mindless.bobhop;

import android.graphics.PointF;


import java.util.ArrayList;

public class Level1 extends LevelData{


    Level1() {

        tiles = new ArrayList<String>();
        this.tiles.add("..........................................................................");
        this.tiles.add("..........................................................................");
        this.tiles.add("..........................................................................");
        this.tiles.add("..........................................................................");
        this.tiles.add("...........111111111111111111s..............s.....ss......ss.....s........");
        this.tiles.add("11111111111111...............111111111111111111111111111111111111111111111");
        this.tiles.add("..........................................................................");
        this.tiles.add("..........................................................................");
        this.tiles.add("..........................................................................");
        this.tiles.add("..........................................................................");
        this.tiles.add("..........................................................................");
        this.tiles.add("..........................................................................");
        this.tiles.add("..........................................................................");
        this.tiles.add("..........................................................................");
        this.tiles.add("..................................s......s................................");
        this.tiles.add("p.........................r11111111111111111111........c.c.c.c.......t.sss");
        this.tiles.add("111111111111111111111111111....................111111111111111111111111111");

        locations = new ArrayList<Location>();

        this.locations.add(new Location("Level2", 1f, 14f));

        backgroundDataList = new ArrayList<BackgroundData>();
        //speeds less than 2 cause issues
        this.backgroundDataList.add(new BackgroundData("world1background1", true, -1, 3, 18, 10, 15));
        this.backgroundDataList.add(new BackgroundData("world1background2", true, 1, 20, 24, 24, 4));
    }
}
