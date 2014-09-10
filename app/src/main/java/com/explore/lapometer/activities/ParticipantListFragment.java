package com.explore.lapometer.activities;



import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.explore.lapometer.LapCategory;
import com.explore.lapometer.R;
import com.explore.lapometer.util.Participant;
import com.explore.lapometer.util.DBClass;
import com.explore.lapometer.views.ParticipantNodeView;
import com.explore.lapometer.util.AppConstants;
import com.explore.lapometer.util.RACE_STATUS;


import java.util.ArrayList;
/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ParticipantListFragment extends android.support.v4.app.Fragment {
    Participant participant;
    ArrayList<Participant> participants;
    SharedPreferences preferences;
    ParticipantNodeView node;
    ArrayList<ParticipantNodeView> participantNodeViews;
    private int nodeCounter = 0;

    public ParticipantListFragment() {
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if( getArguments() != null ) {
            participants = (ArrayList<Participant>) getArguments().get(AppConstants.PARTICIPANTS);
        }
        participantNodeViews = new ArrayList<ParticipantNodeView>();
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        View rootView = inflater.inflate(R.layout.fragment_participant_list, container, false);
        TableLayout tableLayout = new TableLayout(getActivity());
        TableRow row = new TableRow(getActivity());
        int columnCount = 1;
        int rowCount = 0;
        for( int i = 0; i < participants.size(); i++ ) {
            participant = participants.get(i);
            node = new ParticipantNodeView(getActivity());
            node.setNodeColor(Color.WHITE);
            node.setChestCodeColor(Color.WHITE);
            node.setChestCode(participant.getChessCode());
            int height = getActivity().getResources().getDisplayMetrics().heightPixels / AppConstants.ROWS;
            int width =  getActivity().getResources().getDisplayMetrics().widthPixels / AppConstants.COLUMNS;

            node.setPadding(10, 10, 10, 10);
            if( width > height ) {
                width = height;
            } else {
                height = width;
            }
            node.setChestCode(participant.getChessCode());
            participantNodeViews.add(node);
            row.addView(node, new TableRow.LayoutParams(width, height));
            //TODO: Change the static variable to a constant final variable.
            if( columnCount++ % AppConstants.COLUMNS == 0 ) { //After adding 5 views add current row and create new one.
                tableLayout.addView(row, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                row = new TableRow(getActivity());
                row.setGravity(Gravity.LEFT);
                rowCount++;
            }

            if( i == participants.size() - 1 ) {
                tableLayout.addView(row, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                break;
            }

            //TODO: Add the next set of nodes to another view.
            if( rowCount == AppConstants.ROWS ) { //After having maximum no of rows break.
                break;
            }
        }
        LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.rootView);
        tableLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        for( final ParticipantNodeView nodeView: participantNodeViews ) {
            nodeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (preferences.getString(AppConstants.RACE_STATUS, RACE_STATUS.PAUSED).equals(RACE_STATUS.STARTED)) {
                        long time = HomeActivity.getCurrentTime();
                        for( Participant participant: participants ) {
                            if( participant.getChessCode() == nodeView.getChestCode() ) {
                                participant.addLapTime(time);
                            }
                        }
                        Toast.makeText(getActivity().getApplicationContext(), "Participant (" +nodeView.getChestCode() +")split time added", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            nodeView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (preferences.getString(AppConstants.RACE_STATUS, RACE_STATUS.STARTED).equals(RACE_STATUS.PAUSED)) {
                        Intent intent = new Intent(getActivity(), ParticipantDetailsActivity.class);
                        intent.putExtra(AppConstants.LAP_TIMES,
                                new DBClass(getActivity().getApplicationContext()).individualResult(nodeView.getChestCode()));
                        startActivity(intent);
                    }
                    return false;
                }
            });
        }
        layout.addView(tableLayout, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void updateLapTimes() {
        for( Participant participant: participants ) {
            if( participant.getLapCategory() == LapCategory.LAP_CATEGORY1 ) {
                new DBClass(getActivity().getApplicationContext()).insertTimings1( participant.getChessCode(), participant.getLapTimes());
            } else if ( participant.getLapCategory() == LapCategory.LAP_CATEGORY2 ) {
                new DBClass(getActivity().getApplicationContext()).insertTimings2(participant.getChessCode(), participant.getLapTimes());
            } else if ( participant.getLapCategory() == LapCategory.LAP_CATEGORY3 ){
                new DBClass(getActivity().getApplicationContext()).insertTimings3( participant.getChessCode(), participant.getLapTimes());
            }
        }
    }
}
