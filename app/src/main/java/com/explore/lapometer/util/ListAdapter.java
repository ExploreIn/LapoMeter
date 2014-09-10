package com.explore.lapometer.util;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.explore.lapometer.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by I311849 on 9/7/2014.
 */
public class ListAdapter extends ArrayAdapter<Long> {
    Context context;
    int layout;
    ArrayList<Long> lapTimes;

    public ListAdapter(Context context, int layout, ArrayList<Long> lapTimes) {
        super(context, layout, lapTimes);

        this.context= context;
        this.layout = layout;
        this.lapTimes = lapTimes;
    }


    @Override
    public int getCount() {
        return lapTimes.size();
    }

    @Override
    public Long getItem(int position) {
        return lapTimes.get(position);
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
            holder.time = lapTimes.get(position);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+12:00"));
        calendar.setTimeInMillis(holder.time);
        TextView textViewLapTime = (TextView) convertView.findViewById(R.id.textViewLapTime);
        textViewLapTime.setText("#" + (position + 1)
                + " : " + calendar.get(Calendar.HOUR)
                + " : " + calendar.get(Calendar.MINUTE)
                + " : " + calendar.get(Calendar.SECOND)
                + " : " + calendar.get(Calendar.MILLISECOND));
        return convertView;
    }

    class ViewHolder {
        public long time;
    }
}
