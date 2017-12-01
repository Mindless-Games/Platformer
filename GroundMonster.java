package games.mindless.bobhop;


import android.content.Context;
import android.graphics.PointF;

public class GroundMonster extends GameObject {

    //GroundMonsters move back and forth on the x axis between two given waypoints

    private float waypointx1; // left waypoint
    private float waypointx2; //right waypoint
    private int currentWaypoint;
    final float MAX_X_VELOCITY = 3;


    GroundMonster(Context context, float worldStartX, float worldStartY, char type, int pixelsPerMeter) {

        //uncomment when prepared to animate
//        final int Animation_FPS = ;
//        final int ANIMATION_FRAME_COUNT = ;
//        final String BITMAP_NAME = "";

        final float HEIGHT = 1;
        final float WIDTH = 1;

        setHeight(HEIGHT);
        setWidth(WIDTH);

        setType(type);

        setBitmapName("groundmonster");
        setMoves(true);
        setActive(true);
        setVisible(true);

        //set object to be animated //uncomment when animated
//        setAnimFps(ANIMATION_FPS);
//        setAnimFrameCount(ANIMATION_FRAME_COUNT);
//        setBitmapName(BITMAP_NAME);
//        setAnimated(context, pixelsPerMeter, true);


        //where monster starts
        setWorldLocation(worldStartX, worldStartY, 0);
        setxVelocity(-MAX_X_VELOCITY);
        currentWaypoint = 1;
    }

    @Override
    public void update(long fps, float gravity) {
        //heading towards the left
        if(currentWaypoint == 1) {
            if(getWorldLocation().x <= waypointx1) {
                // arrived at waypoint 1
                currentWaypoint = 2;
                setxVelocity(MAX_X_VELOCITY);
                setFacing(RIGHT);
            }
        }

        //heading right
        if(currentWaypoint == 2) {
            if(getWorldLocation().x >= waypointx2) {
                // arrived at waypoint 2
                currentWaypoint = 1;
                setxVelocity(-MAX_X_VELOCITY);
                setFacing(LEFT);
            }
        }

        move(fps);
        setRectHitbox();
    }

    public void setWaypoint(float x1, float x2) {
        waypointx1 = x1;
        waypointx2 = x2;
    }
}
