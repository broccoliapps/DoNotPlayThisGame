

/**
 * LEVEL:           1
 *
 * AUTHOR:          Danny Delott
 *
 * DATE:            10/08/2014
 *
 * DESCRIPTION:     In this level, the user is presented with a clickable level indicator TextView.
 *                  When clicked, the level indicator changes from the default text color to white.
 *
 *
 * HOW TO:          In order to advance to the next level, the user must LONG PRESS on the level indicator
 *                  when the text color is the default text color (#323232).
 *
 */

package level1to5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

import broccoli.donotplaythisgame.ActivitySelector;
import broccoli.donotplaythisgame.R;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class ActivityLevel1 extends ActivitySelector implements View.OnLongClickListener, View.OnClickListener, View.OnTouchListener {

    private TextView tvLevelIndicator;

    // holds the colors of the TextView level indicator
    private final String WHITE = "WHITE";
    private final String TEXT_COLOR = "TEXT_COLOR";
    private String currentTextColor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_1);

        // instantiates TextView level indicator from layout xml file
        tvLevelIndicator = (TextView) findViewById(R.id.tvLevelIndicator);

        // sets the background drawable of the level indicator
        tvLevelIndicator.setBackgroundResource(R.drawable.textview_number_outline_text_color);
        currentTextColor = TEXT_COLOR;

        // sets on click listeners for touch events on the level indicator
        tvLevelIndicator.setOnTouchListener(this);
        tvLevelIndicator.setOnClickListener(this);
        tvLevelIndicator.setOnLongClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // if the user has queued up lots of Crouton messages by spamming the hint,
        // cancel these Croutons when it's time to destroy the Activity.
        Crouton.cancelAllCroutons();
    }

    @Override
    public void onClick(View view) {

        // Changes level indicator to white
        if (currentTextColor.contentEquals(TEXT_COLOR)) {
            tvLevelIndicator.setBackgroundResource(R.drawable.textview_number_outline_white);
            tvLevelIndicator.setTextColor(getResources().getColor(R.color.white));
            currentTextColor = WHITE;
            return;
        }

        // Changes level indicator to text_color (#323232)
        if (currentTextColor.contentEquals(WHITE)) {
            tvLevelIndicator.setBackgroundResource(R.drawable.textview_number_outline_text_color);
            tvLevelIndicator.setTextColor(getResources().getColor(R.color.text_color));
            currentTextColor = TEXT_COLOR;
            return;
        }
    }

    @Override
    public boolean onLongClick(View view) {

        // Transitions to Level 2
        if (currentTextColor.contentEquals(WHITE)) {

            // animates Level Indicator
            YoYo.with(Techniques.RollOut)
                    .duration(700)
                    .interpolate(new AccelerateDecelerateInterpolator())
                    .withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {

                            // Changes activities
                            Intent intent = new Intent(ActivityLevel1.this, ActivityLevel2.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.activity_main_fade_in, R.anim.activity_main_fade_out);

                            // Removes ActivityLevel1 from stack
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

        // Displays Crouton message hint
        if (currentTextColor.contentEquals(TEXT_COLOR)) {

            // wiggles the level indicator
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(tvLevelIndicator);

            Crouton.cancelAllCroutons();
            Crouton.makeText(this, "Hmmm...", Style.INFO).show();


        }


        // returns true to prevent firing onClick() afterward
        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY,
                    HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
        }
        return false;
    }

}
