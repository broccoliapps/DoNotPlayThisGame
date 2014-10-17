

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


public class ActivityLevel3 extends Activity {

    // ///////////////////
    // GLOBAL VARIABLES //
    // ///////////////////

    // holds the swipe field size and velocity thresholds
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    // holds the level indicator TextView
    private TextView tvLevelIndicator;

    // holds the onTouch listener to set for tvLevelIndicator.
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;

    // //////////////////////////
    // PUBLIC OVERRIDE METHODS //
    // //////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_3);

        // instantiates GestureDetector Interface to set in tvLevelIndicator.onTouchListener();
        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };

        // instantiates TextView level indicator from layout xml file
        tvLevelIndicator = (TextView) findViewById(R.id.tvLevelIndicator);
        tvLevelIndicator.setClickable(true);

        // sets on click listener for touch events on the level indicator
        tvLevelIndicator.setOnTouchListener(gestureListener);


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
        intent.putExtra("this_level", Levels.levelNumbers[2]);
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
            case 0:
                Crouton.makeText(ActivityLevel3.this, "C'mon, that's too easy...", Style.INFO).show();
                break;
            case 1:
                Crouton.makeText(ActivityLevel3.this, "Swipe swipe swipe...", Style.INFO).show();
                break;
            default:
                break;
        }

        // animates View that contained the hint
        YoYo.with(Techniques.Tada)
                .duration(700)
                .playOn(tvLevelIndicator);

    }



    // //////////////////
    // PRIVATE CLASSES //
    // //////////////////

    /**
     * The GestureDetector Interface to be used as the tvLevelIndicator's onTouch listener.
     * <p/>
     * This class captures left and right swipe motion.
     */
    private class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {

        // //////////////////////////
        // PUBLIC OVERRIDE METHODS //
        // //////////////////////////

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {

                // bad swipe
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;

                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    showHint(1);
                }

                // left to right swipe
                else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    nextLevel();

                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }


        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            showHint(0);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            if (e.getAction() == MotionEvent.ACTION_DOWN) {
                tvLevelIndicator.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY,
                        HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
            }
            return true;
        }
    }
}
