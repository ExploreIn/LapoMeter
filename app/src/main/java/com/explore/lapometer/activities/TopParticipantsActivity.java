package com.explore.lapometer.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.explore.lapometer.stubs.DBStub;
import com.explore.lapometer.util.ParticipantLapTimeAdapter;

import com.explore.lapometer.R;


public class TopParticipantsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_participants);

        ListView listViewPartLapTime = (ListView) findViewById(R.id.listViewParticipantLapTimes);
        ParticipantLapTimeAdapter participantLapTimeAdapter = new ParticipantLapTimeAdapter(this, R.layout.participant_lap_time, new DBStub(30).getAllParticipants());
        listViewPartLapTime.setAdapter(participantLapTimeAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_participants, menu);
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
}
