package broccoli.donotplaythisgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import level1to5.ActivityLevel1;


public class ActivityMain extends Activity implements View.OnClickListener {

    // RelativeLayout container from the Activity layout
    private RelativeLayout rlContainer;

    // Buttons from the Activity layout
    private Button bSlot1;
    private Button bSlot2;
    private Button bSlot3;
    private Button bHowToPlay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializes Views
        rlContainer = (RelativeLayout) findViewById(R.id.rlContainer);
        bSlot1 = (Button) findViewById(R.id.bSlot1);
        bSlot2 = (Button) findViewById(R.id.bSlot2);
        bSlot3 = (Button) findViewById(R.id.bSlot3);
        bHowToPlay = (Button) findViewById(R.id.bHowToPlay);

        // sets button click listeners
        bSlot1.setOnClickListener(this);
        bSlot2.setOnClickListener(this);
        bSlot3.setOnClickListener(this);
        bHowToPlay.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bSlot1:

                Intent intent = new Intent(this, ActivityLevel1.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

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
