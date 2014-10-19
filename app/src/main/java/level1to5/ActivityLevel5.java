package level1to5;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import broccoli.donotplaythisgame.Levels;
import broccoli.donotplaythisgame.R;

public class ActivityLevel5 extends Activity {

    // ///////////////////
    // GLOBAL VARIABLES //
    // ///////////////////

    // holds the level indicator TextView
    private TextView tvLevelIndicator;
    private Context myContext;
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

        // Start listening for button presses
        myAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int stream = 0;

        myAudioManager.getStreamVolume(stream);

        myMediaPlayer = MediaPlayer.create(this, R.raw.shakeshakeshake);
        myMediaPlayer.setVolume(0, 0);
        myMediaPlayer.start();
        myMediaPlayer.setLooping(true);
    }

    @Override
    public void onBackPressed() {

        // saves data
        Intent intent = new Intent();
        intent.putExtra("level_completed", false);
        intent.putExtra("this_level", Levels.levelNumbers[3]);
        setResult(RESULT_CANCELED, intent);

        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_level5, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


