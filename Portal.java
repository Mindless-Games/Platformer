package games.mindless.bobhop;


//import android.location.Location;

public class Portal extends GameObject {

    Location target;

    Portal(float worldStartX, float worldStartY, char type, Location target) {
        float HEIGHT = 2;
        float WIDTH = 2;

        setHeight(HEIGHT);
        setWidth(WIDTH);

        setType(type);

        //choose bitmap
        setBitmapName("portal");

        this.target = new Location(target.level, target.x, target.y);

        //where time starts
        setWorldLocation(worldStartX, worldStartY - 1f, 0);
        setRectHitbox();
    }

    public Location getTarget(){
        return target;
    }

    @Override
    public void update(long fps, float gravity) {

    }
}
