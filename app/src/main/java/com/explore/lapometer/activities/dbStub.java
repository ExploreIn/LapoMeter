package com.explore.lapometer.activities;

import com.explore.lapometer.interfaces.DBInterface;
import com.explore.lapometer.util.Participant;

import java.util.ArrayList;

/**
 * Dummy class for creating participants
 */
public class dbStub implements DBInterface {
    @Override
    public ArrayList<Participant> getAllParticipants() {
        ArrayList<Participant> participants = new ArrayList<Participant>();
        for( int i = 0; i < 50; i++ ) {
            participants.add(i, new Participant(i));
        }
        return participants;
    }

    @Override
    public Participant getParticipant(int chessCode) {
        return null;
    }
}
