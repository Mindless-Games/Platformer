package games.mindless.bobhop;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class Background {

    Bitmap bitmap;
    Bitmap bitmapReversed;

    int width;
    int height;

    boolean reversedFirst;
    int xClip;
    float y;
    float endY;
    int z;

    float speed;
    boolean isParallax;

    Background(Context context, int yPixelsPerMeter, int screenWidth, BackgroundData data) {

        int resID = context.getResources().getIdentifier(data.bitmapName, "drawable", context.getPackageName());

        bitmap = BitmapFactory.decodeResource(context.getResources(), resID);

        reversedFirst = false;

        //initialize animation variables
        xClip = 0;
        y = data.startY;
        endY = data.endY;
        z = data.layer;
        isParallax = data.isParallax;
        speed = data.speed;

        bitmap = Bitmap.createScaledBitmap(bitmap, screenWidth, data.height * yPixelsPerMeter, true);

        width = bitmap.getWidth();
        height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.setScale(-1, 1);
        bitmapReversed = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }
}
