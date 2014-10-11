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


public class ActivityMain extends Activity implements View.OnClickListener {

    // RelativeLayout container from the Activity layout
    private RelativeLayout rlContainer;

    // TextView logo
    private TextView tvLogo;

    // Buttons from the Activity layout
    private Button bSlot1;
    private Button bSlot2;
    private Button bSlot3;

    private SharedPreferences prefSlot1;
    private SharedPreferences prefSlot2;
    private SharedPreferences prefSlot3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // gets SharedPreferences for game slots
        prefSlot1 = GameData.createNewSharedPreference(this, "slot1",
                MODE_PRIVATE);
        prefSlot2 = GameData.createNewSharedPreference(this, "slot2",
                MODE_PRIVATE);
        prefSlot3 = GameData.createNewSharedPreference(this, "slot3",
                MODE_PRIVATE);

        // initializes Views
        rlContainer = (RelativeLayout) findViewById(R.id.rlContainer);
        tvLogo = (TextView) findViewById(R.id.tvLogo);
        bSlot1 = (Button) findViewById(R.id.bSlot1);
        bSlot2 = (Button) findViewById(R.id.bSlot2);
        bSlot3 = (Button) findViewById(R.id.bSlot3);

        // sets button values
        


        // Sets logo font
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/android-dev-icons-2.ttf");
        tvLogo.setTypeface(face);

        // sets button click listeners
        bSlot1.setOnClickListener(this);
        bSlot2.setOnClickListener(this);
        bSlot3.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences gameData = this.getSharedPreferences("broccoliapps.dnptg.data", 0);
        levelsTried[0] = gameData.getInt("levelsTried1", 0);

        if (levelsTried[0] > 0) {
            bSlot1.setText("Game1");
        }

        if (levelsTried[1] > 0) {
            bSlot1.setText("Game2");
        }
        if (levelsTried[2] > 0) {
            bSlot1.setText("Game3");
        }

    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, ActivitySelector.class);
        switch (view.getId()) {
            case R.id.bSlot1:
                currentGame = 1;
                startActivity(intent);
                break;
            case R.id.bSlot2:
                currentGame = 2;
                startActivity(intent);
                break;
            case R.id.bSlot3:
                currentGame = 3;
                startActivity(intent);
                break;
        }
        overridePendingTransition(R.anim.activity_main_fade_in, R.anim.activity_main_fade_out);
    }
}
