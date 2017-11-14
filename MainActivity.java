package games.mindless.bobhop;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.graphics.Point;

public class MainActivity extends Activity {

    private PlatformView platformView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();

        Point resolution = new Point();
        display.getSize(resolution);

        platformView = new PlatformView(this, resolution.x, resolution.y);

        setContentView(platformView);
    }

    // If the Activity is paused make sure to pause our thread
    @Override
    protected void onPause() {
        super.onPause();
        platformView.pause();
    }

    // If the Activity is resumed make sure to resume our thread
    @Override
    protected void onResume() {
        super.onResume();
        platformView.resume();
    }
}
