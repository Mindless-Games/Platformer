package games.mindless.bobhop;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;

public class Animation {
    Bitmap bitmapSheet;
    String bitmapName;
    private Rect sourceRect;
    private int frameCount;
    private int currentFrame;
    private long frameTicker;
    private int framePeriod;
    private int frameWidth;
    private int frameHeight;
    int pixelsPerMeter;

    Animation(Context context, String bitmapName, float frameHeight, float frameWidth, int animFps,
              int frameCount, int pixelsPerMeter) {

        this.currentFrame = 0;
        this.frameCount = frameCount;
        this.frameWidth = (int)frameWidth * pixelsPerMeter;
        this.frameHeight = (int)frameHeight * pixelsPerMeter;
        sourceRect = new Rect(0, 0, this.frameWidth, this.frameHeight);
        framePeriod = 1000 / animFps;
        frameTicker = 01;
        this.bitmapName = "" + bitmapName;
        this.pixelsPerMeter = pixelsPerMeter;
    }

    public Rect getCurrentFrame(long time, float xVelocity, boolean moves) {
        if(xVelocity !=0 || moves == false) {
            //only animate if the object is moving but is a stationary object like fire
            if(time > frameTicker + framePeriod) {
                frameTicker = time;
                currentFrame++;
                if(currentFrame >= frameCount) {
                    currentFrame = 0;
                }
            }
        }

        this.sourceRect.left = currentFrame * frameWidth;
        this.sourceRect.right = this.sourceRect.left + frameWidth;

        return sourceRect;
    }
}
