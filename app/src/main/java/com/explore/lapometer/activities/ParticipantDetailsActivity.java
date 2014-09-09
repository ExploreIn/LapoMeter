package com.explore.lapometer.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import com.explore.lapometer.util.ListAdapter;

import com.explore.lapometer.R;

import java.util.ArrayList;

public class ParticipantDetailsActivity extends Activity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_details);

        listView = (ListView) findViewById(R.id.listViewLapTimings);
        ListAdapter listAdapter = new ListAdapter(this, R.layout.lap_time_layout, getLapTimes());
        listView.setAdapter(listAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.participant_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    ArrayList<Long> getLapTimes() {
        ArrayList<Long> laps = new ArrayList<Long>();
        for( int i =0; i < 10; i++ ) {
            laps.add(Math.round(Math.random() * 10000 ));
            Log.d("hell", "stored " + laps.get(i));
        }
        return laps;
    }
}