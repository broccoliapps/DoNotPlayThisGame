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
    private Button[] bSlots;

    private SharedPreferences[] slotPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // initializes the SharedPreferences objects
        initializeGameSlotPrefs();

        // initializes the Views from the layout xml file
        initializeViews();

        // sets the text in the Button views according to the SharedPreference values
        setButtonValues();

    }

    /**
     * Creates/initializes the global SharedPreference objects containing the level progress
     * information for each game slot.
     */
    private void initializeGameSlotPrefs() {

        slotPrefs = new SharedPreferences[]{

                GameData.createNewSharedPreference(this, "slot1",
                        MODE_PRIVATE),
                GameData.createNewSharedPreference(this, "slot2",
                        MODE_PRIVATE),
                GameData.createNewSharedPreference(this, "slot3",
                        MODE_PRIVATE)
        };

    }

    /**
     * Initializes the Views from the layout XML file that have interaction.
     */
    private void initializeViews() {
        rlContainer = (RelativeLayout) findViewById(R.id.rlContainer);

        tvLogo = (TextView) findViewById(R.id.tvLogo);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/android-dev-icons-2.ttf");
        tvLogo.setTypeface(face);

        bSlots = new Button[]{
                (Button) findViewById(R.id.bSlot1),
                (Button) findViewById(R.id.bSlot2),
                (Button) findViewById(R.id.bSlot3)
        };

        bSlots[0].setOnClickListener(this);
        bSlots[1].setOnClickListener(this);
        bSlots[2].setOnClickListener(this);
    }

    /**
     * Reads the values from the SharedPreferences and sets the text value in the game slot Buttons.
     */
    private void setButtonValues() {
        int value = -1;
        for (int i = 0; i < slotPrefs.length; i++) {
            value = slotPrefs[i].getInt("currentLevel", -1);
            if (value == -1) {
                bSlots[i].setText(R.string.empty_slot);
            } else {
                bSlots[i].setText("Level " + value);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // sets the text in the Button views according to the SharedPreference values
        setButtonValues();
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, ActivitySelector.class);
        switch (view.getId()) {
            case R.id.bSlot1:
                intent.putExtra("currentGame", "slot1");
                break;
            case R.id.bSlot2:
                intent.putExtra("currentGame", "slot2");
                break;
            case R.id.bSlot3:
                intent.putExtra("currentGame", "slot3");
                break;
        }
        startActivity(intent);
        overridePendingTransition(R.anim.activity_main_fade_in, R.anim.activity_main_fade_out);
    }
}
