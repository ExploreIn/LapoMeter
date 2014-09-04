package com.explore.lapometer.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridView;

import com.explore.lapometer.R;
import com.explore.lapometer.interfaces.DBInterface;
import com.explore.lapometer.util.GridViewAdapter;
import com.explore.lapometer.util.Participant;


public class HomeActivity extends Activity {

    //Private component variables
    GridView gridViewParticipantList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setComponents();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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

    private void setComponents() {
        gridViewParticipantList = (GridView) findViewById(R.id.gridViewParticipantList);

        DBInterface dbInterface = new dbStub();
        GridViewAdapter gridViewAdapter = new GridViewAdapter(
                this,
                R.layout.participant_node_view,
                dbInterface.getAllParticipants());
        Log.d("Tag", "" + (gridViewAdapter.getItem(2)).getChessCode());
        gridViewParticipantList.setAdapter(gridViewAdapter);
    }
}
