package games.mindless.bobhop;

import android.content.Context;

public class Player extends GameObject {

    RectHitbox rectHitboxFeet;
    RectHitbox rectHitboxHead;
    RectHitbox rectHitboxLeft;
    RectHitbox rectHitboxRight;

    final float MAX_X_VELOCITY = 10;
    public boolean isFalling;
    private boolean isJumping;
    private long jumpTime;
    private long maxJumpTime = 700; // jump 7 tenths of a second

    Player(Context context, float worldStartX, float worldStartY, int pixelsPerMeter) {

        final float HEIGHT = 2;
        final float WIDTH = 1;

        setHeight(HEIGHT);
        setWidth(WIDTH);

        //player moves right always.
        setxVelocity(10);
        setyVelocity(0);
        setFacing(RIGHT);
        isFalling = false;

        setMoves(true);
        setActive(true);
        setVisible(true);



        setType('p');

        //choose a bitmap
        //this is a sprite sheet with multiple frames

        setBitmapName("player");

        final int ANIMATION_FPS = 16;
        final int ANIMATION_FRAME_COUNT = 5;

        //set this object to be animated
        setAnimFps(ANIMATION_FPS);
        setAnimFrameCount(ANIMATION_FRAME_COUNT);
        setAnimated(context, pixelsPerMeter, true);

        setWorldLocation(worldStartX, worldStartY, 0);

        rectHitboxFeet = new RectHitbox();
        rectHitboxHead = new RectHitbox();
        rectHitboxLeft = new RectHitbox();
        rectHitboxRight = new RectHitbox();
    }

    @Override
    public void update(long fps, float gravity) {
        //jumping and gravity
        this.setxVelocity(5);
        if(isJumping) {
            long timeJumping = System.currentTimeMillis() - jumpTime;
            if(timeJumping < maxJumpTime) {
                if(timeJumping < maxJumpTime / 2) {
                    this.setyVelocity(-gravity); //going up
                }
                else if(timeJumping > maxJumpTime /2) {
                    this.setyVelocity(gravity); //going down
                }
            }
            else {
                isJumping = false;
            }
        }
        else {
            this.setyVelocity(gravity);
            isFalling = true;
        }
        this.move(fps);

        //update all hitboxes
        Vector2Point5D location = getWorldLocation();
        float lx = location.x;
        float ly = location.y;

        //feet
        rectHitboxFeet.top = ly + getHeight() * .95f;
        rectHitboxFeet.left = lx + getWidth() * .2f;
        rectHitboxFeet.bottom = ly + getHeight() * .98f;
        rectHitboxFeet.right = lx + getWidth() * .8f;

        //head
        rectHitboxHead.top = ly;
        rectHitboxHead.left = lx + getWidth() * .4f;
        rectHitboxHead.bottom = ly + getHeight() * .2f;
        rectHitboxHead.right = lx + getWidth() * .6f;

        //left hitbox
        rectHitboxLeft.top = ly + getHeight() * .2f;
        rectHitboxLeft.left = lx + getWidth() * .2f;
        rectHitboxLeft.bottom = ly + getHeight() * .8f;
        rectHitboxLeft.right = lx + getWidth() * .3f;

        //right hitbox
        rectHitboxRight.top = ly + getHeight() * .2f;
        rectHitboxRight.left = lx + getWidth() * .8f;
        rectHitboxRight.bottom = ly + getHeight() * .8f;
        rectHitboxRight.right = lx + getWidth() * .7f;
    }

    public int checkCollisions(RectHitbox rectHitbox) {
        int collided = 0; //no collisions

        //left
        if(this.rectHitboxLeft.intersects(rectHitbox)) {
            //left has collided
            this.setWorldLocationX(rectHitbox.right - getWidth() * .2f);
            collided = 1;
        }

        //right
        if(this.rectHitboxRight.intersects(rectHitbox)) {
            //right has collided
            this.setWorldLocationX(rectHitbox.left - getWidth() * .8f);
            collided = 1;
        }

        //feet
        if(this.rectHitboxFeet.intersects(rectHitbox)) {
            //feet have collided
            this.setWorldLocationY(rectHitbox.top - getHeight());
            collided = 2;
        }

        //head
        if(this.rectHitboxHead.intersects(rectHitbox)) {
            //head has collided
            this.setWorldLocationY(rectHitbox.bottom);
            collided = 3;
        }
        return collided;
    }

    public void startJump(SoundManager sm) {
        if(!isFalling) {
            if(!isJumping) {
                isJumping = true;
                jumpTime = System.currentTimeMillis();
                sm.playSound("jump");
            }
        }
    }
}
