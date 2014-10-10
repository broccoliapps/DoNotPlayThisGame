package broccoli.donotplaythisgame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.prefs.PreferencesFactory;

import level1to5.ActivityLevel1;


public class ActivityMain extends Activity implements View.OnClickListener {

    // RelativeLayout container from the Activity layout
    private RelativeLayout rlContainer;

    // TextView logo
    private TextView tvLogo;

    // Buttons from the Activity layout
    private Button bSlot1;
    private Button bSlot2;
    private Button bSlot3;
    private Button bHowToPlay;

    //GameData


    public int[] levelsTried = {0,0,0};
    public int currentGame = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences gameData = this.getSharedPreferences("broccoliapps.dnptg.data", 0);
        SharedPreferences.Editor gameDataEditor = gameData.edit();

        gameDataEditor.putInt("levelsTried1",0);
        gameDataEditor.apply();

        //Game Data
        levelsTried[0] = gameData.getInt("levelsTried1", 0);
        //levelsTried[0] = gameData.getInt("levelsTried1", 0);
        levelsTried[1] = gameData.getInt("levelsTried2", 0);
        levelsTried[2] = gameData.getInt("levelsTried3", 0);
        setContentView(R.layout.activity_main);

        // initializes Views
        rlContainer = (RelativeLayout) findViewById(R.id.rlContainer);
        tvLogo = (TextView) findViewById(R.id.tvLogo);
        bSlot1 = (Button) findViewById(R.id.bSlot1);
        bSlot2 = (Button) findViewById(R.id.bSlot2);
        bSlot3 = (Button) findViewById(R.id.bSlot3);
        bHowToPlay = (Button) findViewById(R.id.bHowToPlay);


        if (levelsTried[0]>0) {
            bSlot1.setText("Game1");
        }
        if (levelsTried[1]>0) {
            bSlot1.setText("Game2");
        }
        if (levelsTried[2]>0) {
            bSlot1.setText("Game3");
        }



        // Sets logo font
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/android-dev-icons-2.ttf");
        tvLogo.setTypeface(face);

        // sets button click listeners
        bSlot1.setOnClickListener(this);
        bSlot2.setOnClickListener(this);
        bSlot3.setOnClickListener(this);
        bHowToPlay.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences gameData = this.getSharedPreferences("broccoliapps.dnptg.data", 0);
        levelsTried[0] = gameData.getInt("levelsTried1",0);

        if (levelsTried[0]>0) {
            bSlot1.setText("Game1");
        }

        if (levelsTried[1]>0) {
            bSlot1.setText("Game2");
        }
        if (levelsTried[2]>0) {
            bSlot1.setText("Game3");
        }

    }


    @Override
    public void onClick(View view) {
        SharedPreferences gameData = this.getSharedPreferences("broccoliapps.dnptg.data", 0);
        SharedPreferences.Editor gameDataEditor = gameData.edit();
        switch (view.getId()) {
            case R.id.bSlot1:
                if (levelsTried[0]>0) {
                    gameDataEditor.putInt("currentGame",1);
                    gameDataEditor.apply();
                    Intent intent = new Intent(this, ActivitySelector.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(this, ActivityLevel1.class);
                    startActivity(intent);
                }
                overridePendingTransition(R.anim.activity_main_fade_in, R.anim.activity_main_fade_out);
                break;

            case R.id.bSlot2:
                break;
            case R.id.bSlot3:
                break;
            case R.id.bHowToPlay:
                break;

        }

    }
}
