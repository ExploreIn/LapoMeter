package com.explore.lapometer.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.explore.lapometer.util.AppConstants;
import android.widget.EditText;
import com.explore.lapometer.util.RACE_STATUS;
import com.explore.lapometer.util.DBClass;

import com.explore.lapometer.R;

public class ParticipantCountActivity extends Activity {
    Button buttonPartCount;
    EditText editTextPartCount;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if ( !preferences.getString(AppConstants.RACE_STATUS, RACE_STATUS.STOPPED).equals(RACE_STATUS.STOPPED) ) {
            Intent intent = new Intent(ParticipantCountActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_participant_count);

        setComponents();
    }

    private void setComponents() {
        buttonPartCount = (Button) findViewById(R.id.buttonPartCount);
        editTextPartCount = (EditText) findViewById(R.id.editTextPartCount);

        buttonPartCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( editTextPartCount.getText().toString().isEmpty() ) {
                    editTextPartCount.setError("Enter the no of participants.");
                } else {
                    int count = Integer.parseInt(editTextPartCount.getText().toString());
                    Intent intent = new Intent(ParticipantCountActivity.this, HomeActivity.class);
                    intent.putExtra(AppConstants.PARTICIPANT_COUNT, count);
                    preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putString(AppConstants.RACE_STATUS, RACE_STATUS.FIRST_TIME);
                    edit.putInt(AppConstants.PARTICIPANT_COUNT, count);
                    edit.apply();
                    for( int i = 0; i < count; i++ ) {
                        new DBClass(getApplicationContext()).insertData( i + 1 );
                    }
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.participant_count, menu);
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
