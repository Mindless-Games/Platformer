package games.mindless.bobhop;

import android.content.Context;

public class Player extends GameObject {

    Player(Context context, float worldStartX, float worldStartY, int pixelsPerMeter) {

        final float HEIGHT = 2;
        final float WIDTH = 1;

        setHeight(HEIGHT);
        setWidth(WIDTH);

        setType('p');

        //choose a bitmap
        //this is a sprite sheet with multiple frames

        setBitmapName("player");

        setWorldLocation(worldStartX, worldStartY, 0);
    }

    @Override
    public void update(long fps, float gravity) {

    }
}
