package broccoli.donotplaythisgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

import java.util.HashSet;
import java.util.logging.Level;
import level1to5.ActivityLevel1;

public class ActivitySelector extends Activity implements AdapterView.OnItemClickListener {

    // contains the TextView items that represent the level activities
    GridView gridView;

    // holds animation logic
    final Techniques mAnimTechnique = Techniques.Wave;
    boolean mIsAnimating;

    // shared preferences file
    private SharedPreferences mSlotData;
    private int mCurrentLevel;
    private Intent data;
    private int resultCode;
    private int requestCode;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        updateCurrentLevel();
        if (mCurrentLevel == 1) {
            Intent intent = new Intent(this, Levels.levels[0]);
            startActivityForResult(intent, 1);
        }

        setContentView(R.layout.activity_selector);


        // initializes GridView and sets adapter and touch/click listeners
        gridView = (GridView) findViewById(R.id.gridLevels);
        gridView.setAdapter(new SelectorAdapter(this, Levels.levelNumbers[0]));
        gridView.setOnItemClickListener(this);

        mIsAnimating = false;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.data = data;
        this.resultCode = resultCode;
        this.requestCode = requestCode;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // resets the animation boolean logic to make gridView items clickable again
        mIsAnimating = false;

        if (data.getBooleanExtra("level_completed", false)) {
            GameData.putInt(mSlotData, "currentLevel", resultCode);
            Intent intent = new Intent(this, Levels.levels[resultCode]);
            startActivityForResult(intent, 1);
        }
        updateCurrentLevel();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {


        // user clicked an available level and it's not currently processing an available level
        if (!isLockedLevel(position + 1) && !mIsAnimating) {

            // performs haptic feedback
            v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY,
                    HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);

            // disables the View
            v.setEnabled(false);

            // animates the View
            mIsAnimating = true;
            startLevelWithAnimation(v, position);

        }

        // user clicked a locked level and it's not currently processing a locked level
        if (isLockedLevel(position + 1)) {

            // performs haptic feedback
            v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY,
                    HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);

            // animates the View
            animateLockedLevel(v);

        }
    }
    private void updateCurrentLevel() {

        // gets the SharedPreferences for the game slot (passed in from the Intent)
        mSlotData = GameData.createNewSharedPreference(this, getIntent().getStringExtra
                ("currentGame"), MODE_PRIVATE);

        // gets the current level from the SharedPreferences
        mCurrentLevel = mSlotData.getInt("currentLevel", Levels.DEFAULT_INT);

        // sets current level to level 1 if new game was started
        if (mCurrentLevel == Levels.DEFAULT_INT) {
            mCurrentLevel = Levels.levelNumbers[0];
            GameData.putInt(mSlotData, "currentLevel", mCurrentLevel);
        }

    }

    /**
     * Determines if the level index is locked or available.
     *
     * @param level Level index (starting at 0 for Level 1)
     * @return TRUE if level is locked. FALSE if level is available.
     */
    private boolean isLockedLevel(int level) {
        if (mCurrentLevel > level) {
            return false;
        }
        return true;
    }

    private void animateLockedLevel(View view) {

        // declare as final to give access to AnimatorListener override methods
        final View v = view;

        YoYo.with(mAnimTechnique)
                .duration(400)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(view);
    }

    private void startLevelWithAnimation(View view, int index) {

        // declare as final to give access to AnimatorListener override methods
        final int i = index;

        YoYo.with(mAnimTechnique)
                .duration(400)
                .interpolate(new AccelerateDecelerateInterpolator())
                .withListener(new Animator.AnimatorListener() {

                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Intent intent = new Intent(getApplicationContext(), Levels.levels[i]);
                        startActivityForResult(intent, 1);
                        overridePendingTransition(R.anim.activity_main_fade_in, R.anim.activity_main_fade_out);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                }).playOn(view);

    }
}


class SelectorAdapter extends BaseAdapter {

    private Context mContext;
    private Typeface mFace;
    private int mCurrentLevel;

    public SelectorAdapter(Context c, int currentLevel) {
        mContext = c;
        mFace = Typeface.createFromAsset(mContext.getAssets(),
                "fonts/android-dev-icons-2.ttf");
        mCurrentLevel = currentLevel;
    }

    public int getCount() {
        return 20;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new TextView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        // creates a new TextView
        TextView textView = new TextView(mContext);
        textView.setTextSize(36);
        textView.setGravity(Gravity.CENTER);
        textView.setHeight(Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 72, mContext.getApplicationContext().getResources().getDisplayMetrics())));
        textView.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        textView.setHapticFeedbackEnabled(true);

        // shows the level indicator when level is complete
        if (position <= mCurrentLevel) {
            textView.setBackgroundResource(R.drawable.activity_selector_textview_white);
            textView.setTextColor(mContext.getResources().getColor(R.color.white));
            textView.setText(Levels.levelNumbers[position]);
        }

        // Shows lock when level is not complete
        else {
            textView.setBackgroundResource(R.drawable.activity_selector_textview);
            textView.setTypeface(mFace);
            textView.setText("P");  // lock character
        }

        return textView;
    }
}