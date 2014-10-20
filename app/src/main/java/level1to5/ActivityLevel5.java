package level1to5;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

import java.util.logging.Handler;

import broccoli.donotplaythisgame.AccelerometerListener;
import broccoli.donotplaythisgame.AccelerometerManager;
import broccoli.donotplaythisgame.Levels;
import broccoli.donotplaythisgame.R;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class ActivityLevel5 extends Activity implements AccelerometerListener{

    // ///////////////////
    // GLOBAL VARIABLES //
    // ///////////////////

    // holds the level indicator TextView
    private TextView tvLevelIndicator;
    private AudioManager myAudioManager;
    private MediaPlayer myMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_5);

        // instantiates TextView level indicator from layout xml file
        tvLevelIndicator = (TextView) findViewById(R.id.tvLevelIndicator);

        // sets the background drawable of the level indicator
        tvLevelIndicator.setBackgroundResource(R.drawable.textview_number_outline_text_color);

        // Set up music
        myAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
        myAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,0,0);

        myMediaPlayer = MediaPlayer.create(this, R.raw.shakeshakeshake);
        myMediaPlayer.start();
        myMediaPlayer.setLooping(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (AccelerometerManager.isSupported(this)) {
            AccelerometerManager.startListening(this);
        }
    }

    @Override
    public void onBackPressed() {

        //handles media
        myMediaPlayer.stop();

        // saves data
        Intent intent = new Intent();
        intent.putExtra("level_completed", false);
        intent.putExtra("this_level", Levels.levelNumbers[3]);
        setResult(RESULT_CANCELED, intent);

        super.onBackPressed();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        myMediaPlayer.stop();

        if (AccelerometerManager.isListening()) {
            AccelerometerManager.stopListening();
        }
    }

    public void onAccelerationChanged(float x, float y, float z) {
        // TODO Auto-generated method stub

    }

    public void onShake(float force) {

        // Do your stuff here
        if (myAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC) != 0) {
            nextLevel();
            // Called when Motion Detected
          //Toast.makeText(getBaseContext(), "Motion and Volume detected",
            Toast.makeText(getBaseContext(), "Shake shake shake!",
                    Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Animates the transition to the next level and saves the game slot.
     */
    private void nextLevel() {

        // kills appropriate items
        Crouton.cancelAllCroutons();

        // saves data
        Intent intent = new Intent();
        intent.putExtra("level_completed", true);
        intent.putExtra("this_level", Levels.levelNumbers[4]);
        setResult(RESULT_OK, intent);


        // animates Level Indicator
        YoYo.with(Techniques.Shake)
                .duration(700)
                .interpolate(new AccelerateDecelerateInterpolator())
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        overridePendingTransition(R.anim.activity_main_fade_in, R.anim.activity_main_fade_out);
                        finish();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                })
                .playOn(tvLevelIndicator);
    }

}


