package com.explore.lapometer.activities;



import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.explore.lapometer.util.AppVariables;

import com.explore.lapometer.R;
import com.explore.lapometer.util.Participant;
import com.explore.lapometer.views.ParticipantNodeView;

import java.util.ArrayList;
/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ParticipantListFragment extends android.support.v4.app.Fragment {
    Participant participant;
    ArrayList<Participant> participants;
    public ParticipantListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if( getArguments() != null ) {
            participants = (ArrayList<Participant>) getArguments().get("Participants");
        }

        View rootView = inflater.inflate(R.layout.fragment_participant_list, container, false);
        TableLayout tableLayout = new TableLayout(getActivity());
        TableRow row = new TableRow(getActivity());
        int columnCount = 1;
        int rowCount = 0;
        ParticipantNodeView node;
        for( int i = 0; i < participants.size(); i++ ) {
            participant = participants.get(i);
            node = new ParticipantNodeView(getActivity());
            node.setNodeColor(Color.WHITE);
            node.setChestCodeColor(Color.WHITE);
            int height = getActivity().getResources().getDisplayMetrics().heightPixels / 5;
            int width =  getActivity().getResources().getDisplayMetrics().widthPixels / 4;

            node.setPadding(10, 10, 10, 10);
            if( width > height ) {
                width = height;
            } else {
                height = width;
            }
            node.setChestCode(participant.getChessCode());
            node.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long time = HomeActivity.getCurretTime();
                    participant.addLapTime(time);
                    Toast.makeText(getActivity().getApplicationContext(), "" +time, Toast.LENGTH_LONG).show();
                }
            });

            row.addView(node, new TableRow.LayoutParams(width, height));
            //TODO: Change the static variable to a constant final variable.
            if( columnCount++ % 4 == 0 ) { //After adding 5 views add current row and create new one.
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
            if( rowCount == 6 ) { //After having maximum no of rows break.
                break;
            }
        }
        Log.d("ParticipantListFragment","Row count: " + tableLayout.getWidth());
        LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.rootView);
        tableLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.addView(tableLayout, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
