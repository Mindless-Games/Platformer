package games.mindless.bobhop;



public class SpikeRight extends GameObject {

    SpikeRight(float worldStartX, float worldStartY, char type) {
        final float HEIGHT = .5f;
        final float WIDTH = .5f;

        setHeight(HEIGHT); // 1 block tall
        setWidth(WIDTH); // 1 block wide

        setType(type);

        //choose bitmap
        setBitmapName("spikeright");

        //where time starts
        setWorldLocation(worldStartX + .5f, worldStartY + .25f, 0);
        setRectHitbox();
    }

    @Override
    public void update(long fps, float gravity) {

    }
}
