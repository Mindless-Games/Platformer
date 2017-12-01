package games.mindless.bobhop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public abstract class GameObject {

    private boolean traversable = false;

    private RectHitbox rectHitbox = new RectHitbox();

    private RectHitbox triHitbox1 = new RectHitbox();
    private RectHitbox triHitbox2 = new RectHitbox();
    private RectHitbox triHitbox3 = new RectHitbox();

    private float xVelocity;
    private float yVelocity;
    private boolean moves = false;
    final int LEFT = -1;
    final int RIGHT = 1;
    private int facing;

    private Vector2Point5D worldLocation;
    private float width;
    private float height;

    private boolean active = true;
    private boolean visible = true;
    private int animFrameCount = 1;
    private char type;

    private String bitmapName;

    private Animation anim = null;
    private boolean animated;
    private int animFps = 1;

    public void setAnimFps(int animFps) {
        this.animFps = animFps;
    }

    public void setAnimFrameCount(int animFrameCount) {
        this.animFrameCount = animFrameCount;
    }

    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(Context context, int pixelsPerMeter, boolean animated) {
        this.animated = animated;
        this.anim = new Animation(context, bitmapName, height, width, animFps, animFrameCount, pixelsPerMeter);
    }

    public Rect getRectToDraw(long deltaTime) {
        return anim.getCurrentFrame(deltaTime, xVelocity, isMoves());
    }

    public abstract void update(long fps, float gravity);

    public String getBitmapName() {
        return bitmapName;
    }

    public Bitmap prepareBitmap(Context context, String bitmapName, int pixelsPerMeter) {
        // make a resource id from the bitmapName
        int resID = context.getResources().getIdentifier(bitmapName, "drawable", context.getPackageName());

        //create bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resID);

        //scale bitmap
        //multiply by frames in image
        //default 1 frame
        bitmap = Bitmap.createScaledBitmap(bitmap, (int)(width * animFrameCount * pixelsPerMeter),
                (int)(height * pixelsPerMeter), false);

        return bitmap;
    }

    public void setWorldLocationY(float y) {
        this.worldLocation.y = y;
    }

    public void setWorldLocationX(float x) {
        this.worldLocation.x = x;
    }

    public Vector2Point5D getWorldLocation() {
        return worldLocation;
    }

    public void setWorldLocation(float x, float y, int z) {
        this.worldLocation = new Vector2Point5D();
        this.worldLocation.x = x;
        this.worldLocation.y = y;
        this.worldLocation.z = z;
    }

    void move(long fps) {
        if(xVelocity != 0) {
            this.worldLocation.x += xVelocity / fps;
        }
        if(yVelocity != 0) {
            this.worldLocation.y += yVelocity / fps;
        }
    }

    public void setRectHitbox() {
        rectHitbox.setTop(worldLocation.y);
        rectHitbox.setLeft(worldLocation.x);
        rectHitbox.setBottom(worldLocation.y + height);
        rectHitbox.setRight(worldLocation.x + width);
    }

    public void setTriHitbox() {
        triHitbox1.setTop(worldLocation.y + (2*height/3));
        triHitbox1.setLeft(worldLocation.x + (width/10));
        triHitbox1.setBottom(worldLocation.y + height);
        triHitbox1.setRight(worldLocation.x + (9*width/10));
    }

    RectHitbox getTriHitbox() {
        return triHitbox1;
    }

    RectHitbox getRectHitbox() {
        return rectHitbox;
    }

    public int getfacing() {
        return facing;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }

    public float getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(float xVelocity){
        if(moves) {
            this.xVelocity = xVelocity;
        }
    }

    public float getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(float yVelocity) {
        if(moves) {
            this.yVelocity = yVelocity;
        }
    }

    public boolean isMoves() {
        return moves;
    }

    public void setMoves(boolean moves) {
        this.moves = moves;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setBitmapName(String bitmapName) {
        this.bitmapName = bitmapName;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public void setTraversable() {
        traversable = true;
    }

    public boolean isTraversable() {
        return traversable;
    }
}
