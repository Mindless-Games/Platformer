package games.mindless.bobhop;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;

public class SoundManager {

    private SoundPool soundPool;
    int jump = -1;
    int coin_pickup = -1;
    int teleport = -1;
    int death = -1;

    public void loadSound(Context context) {
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        try{
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            //create fx
            descriptor = assetManager.openFd("jump.ogg");
            jump = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("coin_pickup.ogg");
            coin_pickup = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("teleport.ogg");
            teleport = soundPool.load(descriptor, 0);

            //change sound file
            descriptor = assetManager.openFd("explode.ogg");
            death = soundPool.load(descriptor, 0);

        } catch (IOException e) {
            Log.e("error", "failed to load sound files");
        }
    }

    public void playSound(String sound) {
        switch (sound) {
            case "jump":
                soundPool.play(jump, 1, 1, 0, 0, 1);
                break;
            case "coin_pickup":
                soundPool.play(coin_pickup, 1, 1, 0, 0, 1);
                break;
        }
    }

}
