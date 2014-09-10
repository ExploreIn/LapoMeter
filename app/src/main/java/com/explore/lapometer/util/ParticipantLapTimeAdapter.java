package com.explore.lapometer.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.explore.lapometer.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;


public class ParticipantLapTimeAdapter extends ArrayAdapter<Participant> {
    Context context;
    int layout;
    ArrayList<Participant> participants;

    public ParticipantLapTimeAdapter(Context context, int layout, ArrayList<Participant> participants) {
        super(context, layout, participants);

        this.context= context;
        this.layout = layout;
        this.participants = participants;
    }


    @Override
    public int getCount() {
        return participants.size();
    }

    @Override
    public Participant getItem(int position) {
        return participants.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder;
        Calendar calendar = Calendar.getInstance();
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(layout, parent, false);
            holder.lapTime = participants.get(position).getTotalLapTime();
            holder.profilePic  = participants.get(position).getImage();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+12:00"));
        calendar.setTimeInMillis(holder.lapTime);
        TextView textViewLapTime = (TextView) convertView.findViewById(R.id.textViewPartLapTime);
        textViewLapTime.setText("" + participants.get(position).getChessCode()
                + " | " + calendar.get(Calendar.HOUR)
                + ":" + calendar.get(Calendar.MINUTE)
                + ":" + calendar.get(Calendar.SECOND)
                + ":" + calendar.get(Calendar.MILLISECOND));
        return convertView;
    }

    class ViewHolder {
        public String profilePic;
        public Long lapTime;
    }
}
