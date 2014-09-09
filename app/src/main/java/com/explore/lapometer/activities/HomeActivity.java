package com.explore.lapometer.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.explore.lapometer.util.Participant;

import com.explore.lapometer.R;
import com.explore.lapometer.stubs.DBStub;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.zip.Inflater;

import android.os.Handler;


public class HomeActivity extends FragmentActivity {

    //Private component variables
    int count;
    long init;
    private static long time;
    Handler handler;
    TextView textViewTimer;
    ViewPager viewPager;
    Runnable runnable;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        setContentView(R.layout.activity_home);
        runnable = new Runnable() {
            @Override
            public void run() {
                calendar = Calendar.getInstance();
                time = System.currentTimeMillis() - init;
                String showTime = "" +calendar.get(Calendar.HOUR)
                        +" : " +calendar.get(Calendar.MINUTE)
                        +" : " +calendar.get(Calendar.SECOND)
                        +" : " +calendar.get(Calendar.MILLISECOND);
                textViewTimer.setText("" + showTime);
                handler.postDelayed(this, 1);
            }
        };
        setComponents();
        this.count = getIntent().getIntExtra("Count", 20);
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabPagerAdapter);
    }

    private void setComponents() {
        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.custom_actionbar, null);
        textViewTimer = (TextView) customView.findViewById(R.id.textViewTimer);
        actionBar.setCustomView(customView);
        actionBar.setTitle("");
        actionBar.setDisplayShowCustomEnabled(true);
        customView.findViewById(R.id.textViewTimer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( textViewTimer.getText().toString().equals("Start")) {
                    init = System.currentTimeMillis();
                    handler.post(runnable);
                }
            }
        });
        viewPager = (ViewPager) findViewById(R.id.pager);
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
            Bundle bundle = new Bundle();
            Intent intent = new Intent(HomeActivity.this, TopParticipantsActivity.class);
            startActivity(intent, bundle);
        }
        return super.onOptionsItemSelected(item);
    }

    public class TabPagerAdapter extends FragmentPagerAdapter {
        double pageCount;
        ArrayList<ParticipantListFragment> participantListFragments;
        Bundle bundle;

        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
            ArrayList<Participant> participants = new DBStub(count).getAllParticipants();
            //TODO: replace 24 with constant value
            pageCount = Math.ceil( (double)participants.size() / 24.0 );
            Toast.makeText(getApplicationContext(), "" +pageCount, Toast.LENGTH_LONG).show();
            participantListFragments = new ArrayList<ParticipantListFragment>();
            ArrayList<Participant> participantSubList;


            int startNode = 0;
            for( int i = 0; i <= pageCount; i++ ) {
                if( startNode > participants.size() - 24 ) {
                    participantSubList = new ArrayList<Participant>(participants.subList( startNode, participants.size()));
                } else {
                    participantSubList = new ArrayList<Participant>( participants.subList( startNode, startNode + 24 ));
                    startNode += 24;
                }
                bundle = new Bundle();
                bundle.putSerializable("Participants", participantSubList);
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

    public static long getCurretTime() {
        return time;
    }
}
