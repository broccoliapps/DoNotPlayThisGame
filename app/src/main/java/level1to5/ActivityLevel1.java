package level1to5;

import android.app.Activity;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.TextView;

import broccoli.donotplaythisgame.R;


public class ActivityLevel1 extends Activity implements View.OnLongClickListener, View.OnClickListener {

    TextView tvLevelIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_1);

        tvLevelIndicator = (TextView) findViewById(R.id.tvLevelIndicator);
        tvLevelIndicator.setOnClickListener(this);
        tvLevelIndicator.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY,
                HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
    }


    @Override
    public boolean onLongClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY,
                HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
        return false;
    }
}
