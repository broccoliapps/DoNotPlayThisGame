

/**
 * LEVEL:           2
 *
 * AUTHOR:          Danny Delott
 *
 * DATE:            10/08/2014
 *
 * DESCRIPTION:     In this level, the user is presented with a clickable level indicator TextView.
 *
 * HOW TO:          In order to advance to the next level, the user must SWIPE RIGHT on the level indicator.
 *
 */

package level1to5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

import broccoli.donotplaythisgame.Levels;
import broccoli.donotplaythisgame.R;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class ActivityLevel2 extends Activity implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener {

    // ///////////////////
    // GLOBAL VARIABLES //
    // ///////////////////

    // holds the level indicator TextView
    private TextView tvLevelIndicator;
    private int click_counter;

    // //////////////////////////
    // PUBLIC OVERRIDE METHODS //
    // //////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_2);

        click_counter = 0;

        // instantiates TextView level indicator from layout xml file
        tvLevelIndicator = (TextView) findViewById(R.id.tvLevelIndicator);

        // sets on click listener for touch events on the level indicator
        tvLevelIndicator.setOnTouchListener(this);
        tvLevelIndicator.setOnClickListener(this);
        tvLevelIndicator.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View view) {
        click_counter++;
        showHint(click_counter);
    }

    @Override
    public boolean onLongClick(View view) {
        nextLevel();
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

    @Override
    public void onBackPressed() {

        // saves data
        Intent intent = new Intent();
        intent.putExtra("level_completed", false);
        intent.putExtra("this_level", Levels.levelNumbers[1]);
        setResult(RESULT_CANCELED, intent);
        super.onBackPressed();
        finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // if the user has queued up lots of Crouton messages by spamming the hint,
        // cancel these Croutons when it's time to destroy the Activity.
        Crouton.cancelAllCroutons();
    }


    // holds the onTouch listener to set for tvLevelIndicator.

    // //////////////////
    // PRIVATE METHODS //
    // //////////////////

    /**
     * Animates the transition to the next level and saves the game slot.
     */
    private void nextLevel() {
        Crouton.cancelAllCroutons();


        // saves data
        Intent intent = new Intent();
        intent.putExtra("level_completed", true);
        intent.putExtra("this_level", Levels.levelNumbers[1]);
        setResult(RESULT_OK, intent);

        YoYo.with(Techniques.RollOut)
                .duration(700)
                .interpolate(new AccelerateDecelerateInterpolator())
                .withListener(new Animator.AnimatorListener() {

                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
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

    /**
     * Shows the Crouton hint and animates the View that contained the hint.
     */
    private void showHint(int index) {

        // shows Crouton
        Crouton.cancelAllCroutons();
        switch (index) {
            case 1:
                Crouton.makeText(ActivityLevel2.this, "C'mon, that's too easy...", Style.INFO).show();
                break;
            case 5:
                Crouton.makeText(ActivityLevel2.this, "You click too fast!", Style.INFO).show();
                break;
            default:
                break;
        }

        // animates View that contained the hint
        YoYo.with(Techniques.Tada)
                .duration(700)
                .playOn(tvLevelIndicator);

    }

}
