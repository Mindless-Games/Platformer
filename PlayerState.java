package games.mindless.bobhop;


import android.graphics.PointF;

public class PlayerState {

    private int numCredits;
    private float restartX;
    private float restartY;

    PlayerState() {
        numCredits = 0;
    }

    public void saveLocation(PointF location) {
        restartX = location.x;
        restartY = location.y;
    }

    public PointF loadLocation() {
        return new PointF(restartX, restartY);
    }

    public void gotCredit() {
        numCredits ++;
    }

    public int getCredits(){
        return numCredits;
    }
}
