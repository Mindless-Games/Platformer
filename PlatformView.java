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

    public PlatformView(Context context, int screenWidth, int screenHeight) {
        super(context);
        this.context = context;

        ourHolder = getHolder();
        paint = new Paint();

        //initialize the viewport
        vp = new ViewPort(screenWidth, screenHeight);

        //load first level
        loadLevel("Level1", 15, 2);
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

                }
                else {
                    //set visible flag to false
                    go.setVisible(false);
                }
            }
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

                        //draw appropriate bitmap
                        canvas.drawBitmap(lm.bitmapArray[lm.getBitmapIndex(go.getType())],
                                toScreen2d.left, toScreen2d.top, paint);
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

                //for reset the number of clipped objects each frame
                vp.resetNumClipped();
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
    }
}
