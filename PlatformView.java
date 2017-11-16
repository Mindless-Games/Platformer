package games.mindless.bobhop;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;


public class PlatformView extends SurfaceView implements Runnable {

    private boolean debugging = true;
    private volatile boolean running;
    private Thread gameThread = null;

    //drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    Context context;
    long startFrameTime;
    long timeThisFrame;
    long fps;

    private LevelManager lm;
    private ViewPort vp;
    InputController ic;
    SoundManager sm;
    private PlayerState ps;

    public PlatformView(Context context, int screenWidth, int screenHeight) {
        super(context);
        this.context = context;

        ourHolder = getHolder();
        paint = new Paint();


        //initialize the viewport
        vp = new ViewPort(screenWidth, screenHeight);

        sm = new SoundManager();
        sm.loadSound(context);

        ps = new PlayerState();

        //load first level
        loadLevel("Level1", 0, 9);
    }

    @Override
    public void run() {

        while(running) {
            startFrameTime = System.currentTimeMillis();

            update();
            draw();

            //calculate fps
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if(timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }
        }
    }

    private void update() {
        for(GameObject go : lm.gameObjects) {
            if(go.isActive()) {
                //clip anything offscreen
                if(!vp.clipObjects(go.getWorldLocation().x, go.getWorldLocation().y, go.getWidth(),
                        go.getHeight())) {

                    //set visible flag to true
                    go.setVisible(true);

                    //check collisions with player
                    int hit = lm.player.checkCollisions(go.getRectHitbox());
                    if (hit > 0) {
                        switch (go.getType()) {
                            case 'c':
                                sm.playSound("coin_pickup");
                                go.setActive(false);
                                go.setVisible(false);
                                ps.gotCredit();
                                break;
                            case 's':
                                PointF location;
                                location = new PointF(ps.loadLocation().x, ps.loadLocation().y);
                                lm.player.setWorldLocationX(location.x);
                                lm.player.setWorldLocationY(location.y);
                            default:
                                if(hit == 1) { // runs into something
                                    lm.player.setxVelocity(0);
                                    //lm.player.setPressingRight(false);
                                }
                                if(hit == 2) { // lands on something
                                    lm.player.isFalling = false;
                                }
                                break;
                        }
                    }

                    if(lm.isPlaying()) {
                        go.update(fps, lm.gravity);
                    }

                }
                else {
                    //set visible flag to false
                    go.setVisible(false);
                }
            }
        }
        if(lm.isPlaying()) {
            //reset players location to enter of viewport
            vp.setWorldCenter(lm.gameObjects.get(lm.playerIndex).getWorldLocation().x,
                    lm.gameObjects.get(lm.playerIndex).getWorldLocation().y);
        }
    }

    private void draw() {

        if(ourHolder.getSurface().isValid()) {
            //lock area of memory to draw to
            canvas = ourHolder.lockCanvas();

            //rub out last frame with arbitrary color
            paint.setColor(Color.argb(255,0,0,255));
            canvas.drawColor(Color.argb(255,0,0,255));

            //draw all GameObjects
            Rect toScreen2d = new Rect();

            //draw a layer at a time
            for(int layer = -1; layer <= 1; layer++) {
                for(GameObject go : lm.gameObjects) {
                    //only draw if visible and this layer
                    if(go.isVisible() && go.getWorldLocation().z == layer) {
                        toScreen2d.set(vp.worldToScreen(go.getWorldLocation().x,
                                go.getWorldLocation().y, go.getWidth(), go.getHeight()));

                        if(go.isAnimated()) {
                            if(go.getfacing() == 1) {
                                //Rotate
                                Matrix flipper = new Matrix();
                                flipper.preScale(-1,1);
                                Rect r = go.getRectToDraw(System.currentTimeMillis());
                                Bitmap b = Bitmap.createBitmap(lm.bitmapArray[lm.getBitmapIndex(go.getType())],
                                        r.left, r.top, r.width(), r.height(), flipper, true);
                                canvas.drawBitmap(b, toScreen2d.left, toScreen2d.top, paint);
                            }
                            else {
                                //draw it regular way
                                canvas.drawBitmap(lm.bitmapArray[lm.getBitmapIndex(go.getType())],
                                        go.getRectToDraw(System.currentTimeMillis()), toScreen2d, paint);
                            }
                        }
                        else {
                            //draw the whole bitmap
                            canvas.drawBitmap(lm.bitmapArray[lm.getBitmapIndex(go.getType())],
                                    toScreen2d.left, toScreen2d.top, paint);
                        }

                        //draw appropriate bitmap
                        //canvas.drawBitmap(lm.bitmapArray[lm.getBitmapIndex(go.getType())],
                        //        toScreen2d.left, toScreen2d.top, paint);
                    }
                }
            }
            //text for debugging
            if(debugging) {
                paint.setTextSize(40);
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setColor(Color.argb(255,255,255,255));
                canvas.drawText("fps: " + fps, 10, 60, paint);

                canvas.drawText("num objects: " + lm.gameObjects.size(), 10, 100, paint);

                canvas.drawText("num clipped: " + vp.getNumClipped(), 10, 140, paint);

                canvas.drawText("playerX: " + lm.gameObjects.get(lm.playerIndex).getWorldLocation().x, 10, 180, paint);

                canvas.drawText("playerY: " + lm.gameObjects.get(lm.playerIndex).getWorldLocation().y, 10, 220, paint);

                canvas.drawText("Gravity: " + lm.gravity, 10, 260, paint);

                canvas.drawText("X Velocity: " + lm.gameObjects.get(lm.playerIndex).getxVelocity(), 10, 300, paint);

                canvas.drawText("Y Velocity: " + lm.gameObjects.get(lm.playerIndex).getyVelocity(), 10, 340, paint);

                //for reset the number of clipped objects each frame
                vp.resetNumClipped();
            }

            paint.setColor(Color.argb(80,255,255,255));
            ArrayList<Rect> buttonsToDraw;
            buttonsToDraw = ic.getButtons();

            for(Rect rect : buttonsToDraw) {
                RectF rf = new RectF(rect.left, rect.top, rect.right, rect.bottom);
                canvas.drawRoundRect(rf, 15f, 15f, paint);
            }

            //draw paused text
            if(!this.lm.isPlaying()) {
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setColor(Color.argb(255,255,255,255));

                paint.setTextSize(120);
                canvas.drawText("Paused", vp.getScreenWidth() / 2, vp.getScreenHeight() / 2, paint);
            }

            //unlock and draw the scene
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    //clean up thread if the game is interrupted
    public void pause() {
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("error", "failed to pause thread");
        }

    }

    //make new thread and start it
    public void resume() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void loadLevel(String level, float px, float py) {

        lm = null;

        //create new level manager
        lm = new LevelManager(context, vp.getPixelsPerMeterX(), vp.getScreenWidth(), ic, level, px, py);

        ic = new InputController(vp.getScreenWidth(), vp.getScreenHeight());

        //set the players location as the worlds center
        vp.setWorldCenter(lm.gameObjects.get(lm.playerIndex).getWorldLocation().x,
                lm.gameObjects.get(lm.playerIndex).getWorldLocation().y);

        PointF location = new PointF(px, py);
        ps.saveLocation(location);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if(lm != null) {
            ic.handleInput(motionEvent, lm, sm, vp);
        }
        return true;
    }
}
