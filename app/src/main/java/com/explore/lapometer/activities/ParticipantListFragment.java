package com.explore.lapometer.activities;



import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.explore.lapometer.R;
import com.explore.lapometer.util.Participant;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ParticipantListFragment extends Fragment {

    ArrayList<Participant> participants;
    public ParticipantListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_participant_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if( getArguments() != null ) {
            participants = (ArrayList<Participant>) getArguments().get("Participants");
        }

        TableLayout tableLayout = (TableLayout) view.findViewById(R.id.tableLayoutMain);
        TableRow row = (TableRow) view.findViewById(R.id.tableRowParticipantList);
        row.addView(view.findViewById(R.id.ParticipantNodeView));
        tableLayout.addView(row);

        for( Participant participant: participants) {

        }
    }
}
