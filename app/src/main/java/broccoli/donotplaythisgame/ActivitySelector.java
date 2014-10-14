package broccoli.donotplaythisgame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.GridView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

public class ActivitySelector extends Activity implements AdapterView.OnItemClickListener {

    // ///////////////////
    // GLOBAL VARIABLES //
    // ///////////////////

    // contains the TextView items that represent the level activities
    GridView gridView;
    SelectorAdapter gridViewAdapter;

    // holds animation logic
    final Techniques mAnimTechnique = Techniques.Wave;
    boolean mIsAnimating;

    // shared preferences file
    private SharedPreferences mSlotData;
    private int mHighestLevel;

    // result to pass to and from the level Activities
    private Intent intent;
    private boolean mLevelCompleted;
    private int mLevel;

    // ///////////////////
    // OVERRIDE METHODS //
    // ///////////////////

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // gets highest level from SharedPreferences
        initializeHighestLevel();
        mLevel = mHighestLevel;

        // auto-starts level 1 if new game
        if (mLevel == 1) {
            intent = new Intent(this, Levels.levels[0]);
            startActivityForResult(intent, 1);
        }

        setContentView(R.layout.activity_selector);

        initializeGridView();

        mIsAnimating = false;
        mLevelCompleted = false;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // globalizes extras from Intent data
        mLevelCompleted = data.getBooleanExtra("level_completed", false);
        mLevel = data.getIntExtra("this_level", Levels.DEFAULT_INT);

    }

    @Override
    protected void onResume() {

        // resets the animation boolean logic to make gridView items clickable again
        mIsAnimating = false;

        if (mLevelCompleted) {

            if (mLevel == mHighestLevel) {
                mHighestLevel++;
                GameData.putInt(mSlotData, "highestLevel", mHighestLevel);
                gridViewAdapter.setHighestLevel(mHighestLevel);
                gridViewAdapter.notifyDataSetChanged();
            }

            // launch next level
            mLevel++;
            Intent intent = new Intent(this, Levels.levels[mLevel - 1]);
            startActivityForResult(intent, 1);
        }

        gridViewAdapter.notifyDataSetChanged();

        super.onResume();


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
            startLevelWithAnimation(v, (position));

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

    // //////////////////
    // PRIVATE METHODS //
    // //////////////////

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
        if (level > mHighestLevel) {
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

    /**
     * Animates the level TextView and starts the appropriate level Activity.
     *
     * @param view  the TextView that was clicked (gotten from onItemClick)
     * @param level the level to start (0-indexed)
     */
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

    private void initializeGridView() {

        gridView = (GridView) findViewById(R.id.gridLevels);

        if (gridView.getAdapter() == null) { //Adapter not set yet.
            gridViewAdapter = new SelectorAdapter(this, mHighestLevel);
            gridView.setAdapter(gridViewAdapter);
        } else { //Already has an adapter
            gridViewAdapter.notifyDataSetChanged();
        }

        gridView.setOnItemClickListener(this);
    }

}
