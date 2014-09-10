package com.explore.lapometer.util;

import android.os.Parcelable;

import com.explore.lapometer.LapCategory;
import com.explore.lapometer.LapoMeter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
/**
 * Created by Manu R on 28/08/2014.
 */
public class Participant implements Serializable {
    private int chessCode;
    private String fName;
    private String lName;
    private String image;
    private LapCategory category;
    private ArrayList<Long> lapTimes;

    public Participant(int chessCode) {
        this.chessCode = chessCode;
        lapTimes = new ArrayList<Long>();
    }

    public void setFirstName(String fName) {
        this.fName = fName;
    }

    public void setLastName(String lName) {
        this.lName = lName;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLapTimes(ArrayList<Long> lapTimes) {
        this.lapTimes = lapTimes;
    }

    public String getFristName() {
        return fName;
    }

    public String getLastName() {
        return lName;
    }

    public String getImage() {
        return image;
    }

    public int getChessCode() {
        return chessCode;
    }


    public ArrayList<Long> getLapTimes() {
        return lapTimes;
    }

    public long getLapTime(int index) {
        return lapTimes.get(index);
    }

    public long getTotalLapTime() {
        long totalTime = 0;
        for( long time : lapTimes ) {
            totalTime += time;
        }
        return totalTime;
    }


    public void addLapTime(long time) {
        lapTimes.add(time);
    }

    public int getTotalLaps() {
        return lapTimes.size();
    }

    public LapCategory getLapCategory() {
        if( lapTimes.size() >= 3 && lapTimes.size() < 5 ) {
            return LapCategory.LAP_CATEGORY1;
        } else if ( lapTimes.size() >= 5 && lapTimes.size() < 10 ) {
            return LapCategory.LAP_CATEGORY2;
        } else if( lapTimes.size() >= 10 ) {
            return LapCategory.LAP_CATEGORY3;
        } else {
            return null;
        }
    }
}
