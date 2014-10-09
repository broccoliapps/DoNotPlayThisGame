package broccoli.donotplaythisgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
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

        gridView = (GridView) findViewById(R.id.gridView1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, numbers);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getApplicationContext(), ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
                switch(position) {
                    case 0:
                        Intent intent = new Intent(getApplicationContext(), ActivityLevel1.class);
                        overridePendingTransition(R.anim.activity_main_fade_in, R.anim.activity_main_fade_out);
                        startActivity(intent);
                        break;
                    default:
                }


            }
        });

    }
}
