package broccoli.donotplaythisgame;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SelectorAdapter extends BaseAdapter {

    // ///////////////////
    // GLOBAL VARIABLES //
    // ///////////////////

    private Context mContext;
    private Typeface mFace;
    private int mHighestLevel;

    // //////////////
    // CONSTRUCTOR //
    // //////////////

    public SelectorAdapter(Context c, int highestLevel) {
        mContext = c;
        mFace = Typeface.createFromAsset(mContext.getAssets(),
                "fonts/android-dev-icons-2.ttf");
        mHighestLevel = highestLevel;
    }

    // //////////////////
    // ADAPTER METHODS //
    // //////////////////

    public int getCount() {
        return Levels.levelNumbers.length;
    }

    public Object getItem(int position) {
        return Levels.levels[position];
    }

    public long getItemId(int position) {
        return position;
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

        // shows the level indicator when level is available
        if (position < mHighestLevel) {
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

    // /////////////////
    // GLOBAL SETTERS //
    // /////////////////

    public void setHighestLevel(int level) {
        mHighestLevel = level;
    }

}
