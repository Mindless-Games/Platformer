package games.mindless.bobhop;

import android.graphics.Paint;
import android.graphics.Rect;

public class ViewPort {

    private Vector2Point5D currentViewportWorldCenter;
    private Rect convertedRect;
    private int pixelsPerMeterX;
    private int pixelsPerMeterY;
    private int screenXResolution;
    private int screenYResolution;
    private int screenCenterX;
    private int screenCenterY;
    private int metersToShowX;
    private int metersToShowY;
    private int numClipped;


    ViewPort(int x, int y) {

        screenXResolution = x;
        screenYResolution = y;

        screenCenterX = screenXResolution / 2;
        screenCenterY = screenYResolution / 2;

        pixelsPerMeterX = screenXResolution / 16;
        pixelsPerMeterY = screenYResolution / 9;

        metersToShowX = 34;
        metersToShowY = 20;

        convertedRect = new Rect();
        currentViewportWorldCenter = new Vector2Point5D();
    }

    void setWorldCenter(float x, float y) {
        currentViewportWorldCenter.x = x;
        currentViewportWorldCenter.y = y;
    }

    public int getScreenWidth() {
        return screenXResolution;
    }

    public int getScreenHeight() {
        return screenYResolution;
    }

    public int getPixelsPerMeterX() {
        return pixelsPerMeterX;
    }

    public int getPixelsPerMeterY() {
        return pixelsPerMeterY;
    }

    public int getyCenter() {
        return screenCenterY;
    }

    public float getViewportWorldCenterY() {
        return getPixelsPerMeterY();
    }

    public Rect worldToScreen(float objectX, float objectY, float objectWidth, float objectheight) {
        int left = (int)(screenCenterX - ((currentViewportWorldCenter.x - objectX) * pixelsPerMeterX));

        int top = (int)(screenCenterY - ((currentViewportWorldCenter.y - objectY) * pixelsPerMeterY));

        int right = (int)(left + (objectWidth * pixelsPerMeterX));

        int bottom = (int)(top + (objectheight * pixelsPerMeterY));

        convertedRect.set(left, top, right, bottom);

        return convertedRect;
    }

    public boolean clipObjects(float objectX, float objectY, float objectWidth, float objectHeight) {

        boolean clipped = true;

        if(objectX - objectWidth < currentViewportWorldCenter.x + (metersToShowX / 2)) {
            if(objectX + objectWidth > currentViewportWorldCenter.x - (metersToShowX /2)) {
                if(objectY - objectHeight < currentViewportWorldCenter.y + (metersToShowY /2)) {
                    if(objectY + objectHeight > currentViewportWorldCenter.y - (metersToShowY /2)) {
                        clipped = false;
                    }
                }
            }
        }

        //for debugging
        if(clipped) {
            numClipped++;
        }

        return clipped;
    }

    public int getNumClipped() {
        return numClipped;
    }

    public void resetNumClipped() {
        numClipped = 0;
    }
}
