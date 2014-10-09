

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

import broccoli.donotplaythisgame.R;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class ActivityLevel2 extends Activity {

    private TextView tvLevelIndicator;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_2);

        // Gesture detection
        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    tvLevelIndicator.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY,
                            HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                }

                return gestureDetector.onTouchEvent(event);
            }
        };

        // instantiates TextView level indicator from layout xml file
        tvLevelIndicator = (TextView) findViewById(R.id.tvLevelIndicator);
        tvLevelIndicator.setClickable(true);

        // sets on click listeners for touch events on the level indicator
        tvLevelIndicator.setOnTouchListener(gestureListener);


    }


    public void animateLevelIndicator() {

        YoYo.with(Techniques.RollOut)
                .duration(700)
                .interpolate(new AccelerateDecelerateInterpolator())
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {


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


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // if the user has queued up lots of Crouton messages by spamming the hint,
        // cancel these Croutons when it's time to destroy the Activity.
        Crouton.cancelAllCroutons();
    }


    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                    tvLevelIndicator.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY,
                            HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);

                    Crouton.cancelAllCroutons();
                    Crouton.makeText(ActivityLevel2.this, "Swipe swipe swipe...", Style.INFO).show();


                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                    tvLevelIndicator.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY,
                            HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);

                    animateLevelIndicator();

                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }
}
