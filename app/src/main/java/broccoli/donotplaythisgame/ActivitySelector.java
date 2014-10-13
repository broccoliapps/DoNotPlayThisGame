package broccoli.donotplaythisgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
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

public class ActivitySelector extends Activity implements AdapterView.OnItemClickListener {

    // contains the TextView items that represent the level activities
    GridView gridView;
    SelectorAdapter gridViewAdapter;

    // holds animation logic
    final Techniques mAnimTechnique = Techniques.Wave;
    boolean mIsAnimating;

    // shared preferences file
    private SharedPreferences mSlotData;
    private int mHighestLevel;
    private int mCurrentLevel;  // 1-indexed

    // result to pass to and from the level Activities
    private Intent intent;
    private boolean isLevelCompleted;
    private int levelCompleted;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // gets current level from SharedPreferences
        initializeHighestLevel();
        mCurrentLevel = mHighestLevel;

        // auto-starts level 1 if new game
        if (mCurrentLevel == 1) {
            intent = new Intent(this, Levels.levels[0]);
            startActivityForResult(intent, 1);
        }

        setContentView(R.layout.activity_selector);

        initializeGridView();

        mIsAnimating = false;

    }

    private void initializeGridView() {
        // initializes GridView and sets adapter and touch/click listeners
        gridView = (GridView) findViewById(R.id.gridLevels);
        gridViewAdapter = new SelectorAdapter(this, mCurrentLevel);
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        isLevelCompleted = data.getBooleanExtra("level_completed", false);
        levelCompleted = data.getIntExtra("this_level", Levels.DEFAULT_INT);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // resets the animation boolean logic to make gridView items clickable again
        mIsAnimating = false;

        // updates SharedPreferences when level is completed
        if (isLevelCompleted) {

            if (levelCompleted == mCurrentLevel) {
                mHighestLevel++;
            }

            GameData.putInt(mSlotData, "currentLevel", mCurrentLevel);

            // launch next level
            Intent intent = new Intent(this, Levels.levels[mCurrentLevel - 1]);
            startActivityForResult(intent, 1);
        }

        gridViewAdapter.notifyDataSetChanged();
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
            startLevelWithAnimation(v, (position + 1));

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

    /**
     * Initializes the value at mHighestLevel from the SharedPreferences.
     */
    private void initializeHighestLevel() {

        // gets the SharedPreferences for the game slot (passed in from the Intent)
        mSlotData = GameData.createNewSharedPreference(this, getIntent().getStringExtra
                ("currentGame"), MODE_PRIVATE);

        // gets the current level from the SharedPreferences
        mHighestLevel = mSlotData.getInt("highestLevel", Levels.DEFAULT_INT);

        // sets current level to level 1 if new game was started
        if (mHighestLevel == Levels.DEFAULT_INT) {
            mHighestLevel = Levels.levelNumbers[0];
            GameData.putInt(mSlotData, "highestLevel", mHighestLevel);
        }

    }

    /**
     * Determines if the level is locked or available.
     *
     * @param level Level (1-indexed)
     * @return TRUE if level is locked. FALSE if level is available.
     */
    private boolean isLockedLevel(int level) {
        if (level > mCurrentLevel) {
            return true;
        }
        return false;
    }

    private void animateLockedLevel(View view) {

        // declare as final to give access to AnimatorListener override methods
        final View v = view;

        YoYo.with(mAnimTechnique)
                .duration(400)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(view);
    }

    private void startLevelWithAnimation(View view, int level) {

        // declare as final to give access to AnimatorListener override methods
        final int i = level;

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
        if (position < mCurrentLevel) {
            textView.setBackgroundResource(R.drawable.activity_selector_textview_white);
            textView.setTextColor(mContext.getResources().getColor(R.color.white));
            textView.setText(Integer.toString(Levels.levelNumbers[position]));
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