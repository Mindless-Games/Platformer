package games.mindless.bobhop;



public class Spike extends GameObject {

    Spike(float worldStartX, float worldStartY, char type) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        setHeight(HEIGHT); // 1 block tall
        setWidth(WIDTH); // 1 block wide

        setType(type);

        //choose bitmap
        setBitmapName("spike");

        //where tile starts
        setWorldLocation(worldStartX, worldStartY, 0);
        setRectHitbox();
    }

    @Override
    public void update(long fps, float gravity) {

    }
}
