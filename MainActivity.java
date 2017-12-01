package games.mindless.bobhop;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.graphics.Point;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import static games.mindless.bobhop.R.layout.activity_main;
import static games.mindless.bobhop.R.layout.worldselect;

public class MainActivity extends Activity {

    private PlatformView platformView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        Point resolution = new Point();
        display.getSize(resolution);

        platformView = new PlatformView(this, resolution.x, resolution.y);

        setContentView(activity_main);


        //MENU BUTTONS

        //Main Menu
        final Button button = findViewById(R.id.StartButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.worldselect);

                //World Select
                //world 1 || forest world
                final ImageButton button1 = findViewById(R.id.world1Button);
                button1.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        setContentView(platformView);
                    }
                });

                //world 2 || sand world
                final ImageButton button2 = findViewById(R.id.world2Button);
                button2.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        setContentView(platformView);
                    }
                });

                //world 3 || ice world
                final ImageButton button3 = findViewById(R.id.world3Button);
                button3.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        setContentView(platformView);
                    }
                });

                //world 4 || dark world
                final ImageButton button4 = findViewById(R.id.world2Button);
                button4.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        setContentView(platformView);
                    }
                });
            }
        });
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
