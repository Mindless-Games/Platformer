package games.mindless.bobhop;


import android.graphics.PointF;

public class PlayerState {

    private int numCredits;
    private int lives;
    private float restartX;
    private float restartY;

    PlayerState() {
        numCredits = 0;
        lives = 3;
    }

    public void saveLocation(PointF location) {
        restartX = location.x;
        restartY = location.y;
    }

    public PointF loadLocation() {
        return new PointF(restartX, restartY);
    }

    public int getLives() {
        return lives;
    }

    public void setLives() {
        this.lives = lives;
    }

    public void loseLife() {
        lives--;
    }

    public void addLife() {
        lives++;
    }

    public void resetLives() {
        lives = 3;
    }

    public void gotCredit() {
        numCredits ++;
    }

    public int getCredits(){
        return numCredits;
    }
}
