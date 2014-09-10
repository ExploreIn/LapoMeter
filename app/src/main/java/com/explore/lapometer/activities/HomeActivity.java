package com.explore.lapometer.activities;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import com.explore.lapometer.util.DBClass;

import com.explore.lapometer.util.Participant;

import com.explore.lapometer.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import com.explore.lapometer.util.AppConstants;
import com.explore.lapometer.util.RACE_STATUS;

import android.os.Handler;


public class HomeActivity extends FragmentActivity {

    //Private component variables
    ArrayList<ParticipantListFragment> participantListFragments;
    int count;
    private static long init;
    private static long time;
    Handler handler;
    TextView textViewTimer;
    ViewPager viewPager;
    Runnable runnable;
    Calendar calendar;
    SharedPreferences manager;
    Menu actionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        setContentView(R.layout.activity_home);
        manager = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        calendar = Calendar.getInstance();
        runnable = new Runnable() {
            @Override
            public void run() {
                calendar.setTimeZone(TimeZone.getTimeZone("GMT+12:00"));
                time = System.currentTimeMillis() - init;
                calendar.setTimeInMillis(time);
                String showTime = "" +calendar.get(Calendar.HOUR)
                        +" : " +calendar.get(Calendar.MINUTE)
                        +" : " +calendar.get(Calendar.SECOND)
                        +" : " +calendar.get(Calendar.MILLISECOND);
                textViewTimer.setText("" + showTime);
                handler.postDelayed(this, 1);
            }
        };
        setComponents();
        this.count = manager
                .getInt(AppConstants.PARTICIPANT_COUNT,
                        AppConstants.ROWS*AppConstants.COLUMNS);
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabPagerAdapter);
    }

    private void setComponents() {
        //Setting the custom action bar.
        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.custom_actionbar, null);
        actionBar.setCustomView(customView);
        actionBar.setTitle("");
        actionBar.setDisplayShowCustomEnabled(true);
        textViewTimer = (TextView) customView.findViewById(R.id.textViewTimer);
        textViewTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (manager.getString(AppConstants.RACE_STATUS, RACE_STATUS.PAUSED).equals(RACE_STATUS.FIRST_TIME)) {
                    textViewTimer.setText("START");
                    init = System.currentTimeMillis();
                    actionMenu.findItem(R.id.action_stop).setEnabled(true);
                    SharedPreferences.Editor edit = manager.edit();
                    edit.putString(AppConstants.RACE_STATUS, RACE_STATUS.STARTED);
                    edit.apply();
                    handler.post(runnable);
                } else if (manager.getString(AppConstants.RACE_STATUS, RACE_STATUS.PAUSED).equals(RACE_STATUS.PAUSED)) {
                    getActionBar().setTitle("Paused");
                }
            }
        });
        //====================================================================
        viewPager = (ViewPager) findViewById(R.id.pager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        this.actionMenu = menu;

        if ( manager.getString(AppConstants.RACE_STATUS, RACE_STATUS.PAUSED).equals(RACE_STATUS.FIRST_TIME ) ) {
            actionMenu.findItem(R.id.action_score).setEnabled(false);
            actionMenu.findItem(R.id.action_stop).setEnabled(false);
        } else if ( manager.getString(AppConstants.RACE_STATUS, RACE_STATUS.PAUSED).equals(RACE_STATUS.PAUSED) ) {
            actionMenu.findItem(R.id.action_score).setEnabled(true);
            actionMenu.findItem(R.id.action_stop).setEnabled(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_score) {
            Bundle bundle = new Bundle();
            Intent intent = new Intent(HomeActivity.this, CategorySelectionActivity.class);
            startActivity(intent, bundle);
        }

        if( id == R.id.action_stop ) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirm Action");
            builder.setMessage("Are you sure, you want to stop the race?");
            builder.setPositiveButton("Stop", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    handler.removeCallbacks(runnable);
                    actionMenu.findItem(R.id.action_score).setEnabled(true);
                    for ( ParticipantListFragment fragment: participantListFragments ) {
                        fragment.updateLapTimes();
                    }
                    SharedPreferences.Editor edit = manager.edit();
                    edit.putString(AppConstants.RACE_STATUS, RACE_STATUS.PAUSED);
                    actionMenu.findItem(R.id.action_stop).setEnabled(false);
                    edit.apply();
                    try {
                        getActionBar().setDisplayShowCustomEnabled(false);
                    } catch ( NullPointerException e ) {
                        e.printStackTrace();
                    }
                    getActionBar().setTitle("Paused");
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });

            builder.setCancelable(true);
            builder.show();
        }

        if( id == R.id.action_clear_db ) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirm Action");
            builder.setMessage("Are you sure, this will clear all data?");
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    new DBClass(getApplicationContext()).clearDB();
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putString(AppConstants.RACE_STATUS, RACE_STATUS.STOPPED);
                    edit.apply();
                    Intent intent = new Intent(HomeActivity.this, ParticipantCountActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            builder.setCancelable(true);
            builder.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public class TabPagerAdapter extends FragmentPagerAdapter {
        double pageCount;
        Bundle bundle;

        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
            int pageNodesCount = AppConstants.ROWS * AppConstants.COLUMNS;
            ArrayList<Participant> participants = new DBClass(getApplicationContext()).getAllParticipants();
            int totalNodes = participants.size();
            Log.d("Read part count", "" + participants.size());
            pageCount = Math.ceil( (double)participants.size() / (double)(pageNodesCount));
            participantListFragments = new ArrayList<ParticipantListFragment>();
            ArrayList<Participant> participantSubList;

            int startNode = 0;
            for( int i = 0; i < pageCount; i++ ) {
                if( startNode > participants.size() - pageNodesCount ) {
                    participantSubList = new ArrayList<Participant>(participants.subList( startNode, totalNodes ));
                } else {
                    participantSubList = new ArrayList<Participant>( participants.subList( startNode, startNode + pageNodesCount  ));
                    startNode += pageNodesCount;
                }
                bundle = new Bundle();
                bundle.putSerializable(AppConstants.PARTICIPANTS, participantSubList);
                ParticipantListFragment participantListFragment = new ParticipantListFragment();
                participantListFragment.setArguments(bundle);
                participantListFragments.add(participantListFragment);
            }
        }

        @Override
        public android.support.v4.app.Fragment getItem(int i) {
            return participantListFragments.get(i);
        }

        @Override
        public int getCount() {
            return (int)pageCount;
        }
    }

    public static long getCurrentTime() {
        return time;
    }
}
