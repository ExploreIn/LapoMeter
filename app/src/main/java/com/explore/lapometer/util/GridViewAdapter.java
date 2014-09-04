package com.explore.lapometer.util;

import android.app.Activity;
import android.content.Context;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.explore.lapometer.R;
import com.explore.lapometer.views.ParticipantNodeView;

import java.util.ArrayList;

/**
 * GridViewAdapter for showing custom ParticipantNodes
 */
public class GridViewAdapter extends ArrayAdapter<Participant> {
    private int layout;
    private Context context;
    private ArrayList<Participant> participants;

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList<Participant> participants ) {
        super( context, layoutResourceId, participants );
        this.context = context;
        this.layout = layoutResourceId;
        this.participants = participants;
    }

    @Override
    public int getCount() {
        return participants.size();
    }

    @Override
    public Participant getItem(int i) {
        return participants.get(i);
    }

    @Override
    public long getItemId(int i) {
        return participants.get(i).getChessCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if( row == null ) {
            LayoutInflater inflater = ( (Activity) context).getLayoutInflater();
            row = inflater.inflate(layout, parent, false);
            row.setTag(getItem(position));
            Log.d("Grid Position", "" +position );
            Log.d("Chess Code", "" +getItem(position).getChessCode() );
        }
        ((ParticipantNodeView) row.findViewById(R.id.ParticipantNodeView)).setChestCode(((Participant)row.getTag()).getChessCode());
        (row.findViewById(R.id.ParticipantNodeView)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Clicked", "" +((ParticipantNodeView)view).getChestCode());
            }
        });
        Log.d("Here", "hello again");

        return row;
    }
}
