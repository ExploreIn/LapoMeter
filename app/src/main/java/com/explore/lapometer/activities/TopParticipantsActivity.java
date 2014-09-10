package com.explore.lapometer.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.explore.lapometer.util.AppConstants;
import com.explore.lapometer.util.Participant;
import com.explore.lapometer.util.ParticipantLapTimeAdapter;
import com.explore.lapometer.util.DBClass;

import com.explore.lapometer.R;

import java.util.ArrayList;


public class TopParticipantsActivity extends Activity {
    ArrayList<Participant> participants = new ArrayList<Participant>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_participants);

        int category = getIntent().getIntExtra(AppConstants.CATEGORY, 0);
        switch (category) {
            case 1:
                participants = new DBClass(getApplicationContext()).getResult3();
                break;
            case 2:
                participants = new DBClass(getApplicationContext()).getResult5();
                break;
            case 3:
                participants = new DBClass(getApplicationContext()).getResult10();
                break;
        }

        ListView listViewPartLapTime = (ListView) findViewById(R.id.listViewParticipantLapTimes);
        ParticipantLapTimeAdapter participantLapTimeAdapter = new ParticipantLapTimeAdapter(this, R.layout.participant_lap_time, participants);
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
        if (id == R.id.action_exit) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
