package broccoli.donotplaythisgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import level1to5.ActivityLevel1;

public class ActivitySelector extends ActivityMain {

    GridView gridView;

    static final String[] numbers = new String[] {
            "01", "02", "03", "04", "05",
            "06", "07", "08", "09", "10",
            "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_selector);

        gridView = (GridView) findViewById(R.id.gridLevels);

        gridView.setAdapter(new ImageAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                switch(position) {
                    case 0:
                        Intent intent = new Intent(getApplicationContext(), ActivityLevel1.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.activity_main_fade_in, R.anim.activity_main_fade_out);
                        break;
                    default:
                }


            }
        });

    }
}

class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
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

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView textView = new TextView(mContext);
        textView.setTextSize(36);
        textView.setGravity(Gravity.CENTER);
        textView.setHeight(225);
        textView.setWidth(150);
        textView.setBackgroundResource(R.drawable.gridview_number_outline_text_color);
        //textView.setPadding(8, 8, 8, 8);

        if (convertView == null) {  // if it's not recycled, initialize some attributes
            textView.setPadding(8, 8, 8, 8);
            //textView.setBackgroundResource(R.drawable.gridview_number_outline_text_color);
        } else {
            //imageView = (ImageView) convertView;
        }
        textView.setText(numbers[position]);

        //imageView.setImageResource(mThumbIds[position]);
        return textView;
    }

    private String[] numbers = new String[] {
            "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20"};

    //};
}