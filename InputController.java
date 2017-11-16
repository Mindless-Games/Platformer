package games.mindless.bobhop;


import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.ViewGroup;

import java.util.ArrayList;

public class InputController {

    Rect jump;
    Rect pause;

    InputController(int screenWidth, int screenHeight) {

        int buttonWidth = screenWidth / 8;
        int buttonHeight = screenHeight / 7;
        int buttonPadding = screenWidth / 80;

        jump = new Rect(screenWidth - buttonWidth - buttonPadding,
                screenHeight - buttonHeight - buttonPadding - buttonHeight - buttonPadding,
                screenWidth - buttonPadding,
                screenHeight - buttonPadding - buttonHeight - buttonPadding);

        pause = new Rect(screenWidth - buttonPadding - buttonWidth,
                buttonPadding,
                screenWidth - buttonPadding,
                buttonPadding + buttonHeight);
    }

    public ArrayList getButtons() {
        ArrayList<Rect> currentButtonList = new ArrayList<>(); //empty unless buttons added
        currentButtonList.add(jump);
        currentButtonList.add(pause);
        return currentButtonList;
    }

    public void handleInput(MotionEvent motionEvent, LevelManager l, SoundManager sound, ViewPort vp) {

        int pointerCount = motionEvent.getPointerCount();

        for(int i = 0; i < pointerCount; i++) {

            int x = (int)motionEvent.getX(i);
            int y = (int)motionEvent.getY(i);

            if (l.isPlaying()) {
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        if(jump.contains(x,y)) {
                            l.player.startJump(sound);
                        } else if (pause.contains(x, y)) {
                            l.switchPlayingStatus();
                        }

                        break;
                }
            }
            else {// Not playing
                //Move the viewport around to explore the map
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:

                        if (pause.contains(x, y)) {
                            l.switchPlayingStatus();
                            //Log.w("pause:", "DOWN" );
                        }

                        break;


                }
            }
        }
    }
}
