package games.mindless.bobhop;


public class Grass extends GameObject {

    Grass(float worldStartX, float worldStartY, char type) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        setHeight(HEIGHT); // 1 block tall
        setWidth(WIDTH); // 1 block wide

        setType(type);

        //choose bitmap
        setBitmapName("grass");

        //where time starts
        setWorldLocation(worldStartX, worldStartY, 0);
    }

    @Override
    public void update(long fps, float gravity) {

    }
}
