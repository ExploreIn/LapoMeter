package com.explore.lapometer.interfaces;

import com.explore.lapometer.util.Participant;

import java.util.ArrayList;

/**
 * Created by Manu R on 01/09/2014.
 */
public interface DBInterface {
    public ArrayList<Participant> getAllParticipants();
    public Participant getParticipant(int chessCode);
}
