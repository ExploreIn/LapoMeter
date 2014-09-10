package com.explore.lapometer.util;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.explore.lapometer.interfaces.DBInterface;

public class DBClass extends SQLiteOpenHelper implements DBInterface {

    private static final String DATABASE_NAME = "Lapometer.db";
    private static final int DATABASE_VERSION = 1;

    public DBClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        // @Override
        db.execSQL("CREATE TABLE IF NOT EXISTS participant(chestNo int primary key, firstName String, lastName String, imagePath String, categoryNo int)");
        db.execSQL("CREATE TABLE IF NOT EXISTS category1(chestNo int, lap1 float, lap2 float,lap3 float, FOREIGN KEY(chestNo) REFERENCES participant(chestNo))");
        db.execSQL("CREATE TABLE IF NOT EXISTS category2(chestNo int, lap1 float, lap2 float,lap3 float, lap4 float, lap5 float, FOREIGN KEY(chestNo) REFERENCES participant(chestNo))");
        db.execSQL("CREATE TABLE IF NOT EXISTS category3(chestNo int, lap1 float, lap2 float,lap3 float, lap4 float, lap5 float, lap6 float, lap7 float,lap8 float, lap9 float, lap10 float, FOREIGN KEY(chestNo) REFERENCES participant(chestNo))");

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		/* This is to clear the DB records
		 * The data in the category tables will be cleared off
		 */

        db.execSQL("delete * from category1");
        db.execSQL("delete * from category2");
        db.execSQL("delete * from category3");
    }

    public boolean insertData(int chestNo) {
        // TODO Auto-generated method stub

        // Chest Number of each participant is accepted and inserted into the
        // first table

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("chestNo", chestNo);

        long result = db.insert("participant", null, contentValues);
        return true;

    }

    public boolean insertData(int chestNo, String firstName, String lastName) {
        // TODO Auto-generated method stub

        // Chest Number, First Name, Last Name and the Image Path of each
        // participant is accepted and inserted into the first table

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("chestNo", chestNo);
        contentValues.put("firstName", firstName);
        contentValues.put("lastName", lastName);

        db.insert("participant", null, contentValues);
        return true;

    }

    public boolean insertData(int chestNo, String firstName, String lastName,
                              String imagePath) {
        // TODO Auto-generated method stub

        // The Lap Timings related to each Participant or the chest number is
        // inserted into the second table. The Lap Timings is accepted as an
        // Array List

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("chestNo", chestNo);
        contentValues.put("firstName", firstName);
        contentValues.put("lastName", lastName);
        contentValues.put("imagePath", imagePath);

        db.insert("participant", null, contentValues);
        return true;

    }

    public ArrayList<Participant> getAllParticipants() {
        ArrayList<Participant> result3 = new ArrayList<Participant>();
        try {
            Cursor c = null;
            SQLiteDatabase db = this.getReadableDatabase();
            c = db.rawQuery("SELECT * from participant", null);

            while (c.moveToNext()) {
                int chestNo = c.getInt(c.getColumnIndex("chestNo"));
                Log.d("DB", "read Chest" + chestNo);
                Participant participant = new Participant(chestNo);
                participant.setLapTimes(individualResult(chestNo));
                result3.add(participant);
            }
            c.close();
        }

        catch (Exception e) {
            // body
        }
        return result3;
    }

    public boolean insertTimings1(int chestNo, ArrayList<Long> lapTimings) {
        // TODO Auto-generated method stub

        // The Lap Timings related to category 1 of each Participant or the
        // chest number is inserted into the second table. The Lap Timings is
        // accepted as an Array List

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("chestNo", chestNo);
        contentValues.put("lap1", lapTimings.get(0));
        contentValues.put("lap2", lapTimings.get(1));
        contentValues.put("lap3", lapTimings.get(2));

        long l = db.insert("category1", null, contentValues);
        Log.d("DB", "Times 1:" +l);
        String strSQL = "UPDATE participant SET categoryNo = 3 WHERE chestNo = "
                + chestNo;

        db.execSQL(strSQL);
        return true;

    }

    public boolean insertTimings2(int chestNo, ArrayList<Long> lapTimings) {
        // TODO Auto-generated method stub

        // The Lap Timings related to category 2 of each Participant or the
        // chest number is inserted into the second table. The Lap Timings is
        // accepted as an Array List

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("chestNo", chestNo);
        contentValues.put("lap1", lapTimings.get(0));
        contentValues.put("lap2", lapTimings.get(1));
        contentValues.put("lap3", lapTimings.get(2));
        contentValues.put("lap4", lapTimings.get(3));
        contentValues.put("lap5", lapTimings.get(4));

       long l = db.insert("category2", null, contentValues);
        Log.d("DB", "Times 1:" +l);

        String strSQL = "UPDATE participant SET categoryNo = 5 WHERE chestNo = "
                + chestNo;

        db.execSQL(strSQL);

        return true;

    }

    public boolean insertTimings3(int chestNo, ArrayList<Long> lapTimings) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("chestNo", chestNo);
        contentValues.put("lap1", lapTimings.get(0));
        contentValues.put("lap2", lapTimings.get(1));
        contentValues.put("lap3", lapTimings.get(2));
        contentValues.put("lap4", lapTimings.get(3));
        contentValues.put("lap5", lapTimings.get(4));
        contentValues.put("lap6", lapTimings.get(5));
        contentValues.put("lap7", lapTimings.get(6));
        contentValues.put("lap8", lapTimings.get(7));
        contentValues.put("lap9", lapTimings.get(8));
        contentValues.put("lap10", lapTimings.get(9));

        long l = db.insert("category3", null, contentValues);
        Log.d("DB", "Times 1:" +l);

        String strSQL = "UPDATE participant SET categoryNo = 10 WHERE chestNo = "
                + chestNo;

        db.execSQL(strSQL);

        return true;

    }

    public void clearDB() {
        SQLiteDatabase database = this.getWritableDatabase();
        int r = database.delete("participant", null, null);
        database.delete("category1", null, null);
        database.delete("category2", null, null);
        database.delete("category3", null, null);

        Log.d("DBDelete", "" +r);
    }

    public ArrayList<Long> individualResult(int chestNo) {

        ArrayList<Long> individual_lap_timings = new ArrayList<Long>();
        try {
            Cursor c = null;
            SQLiteDatabase db = this.getReadableDatabase();
            c = db.rawQuery(
                    "SELECT categoryNo from participant where chestNo = "
                            + chestNo, null);
            c.moveToFirst();
            int catNo = c.getInt(c.getColumnIndex("categoryNo"));
            Log.d("CategoryNo", "" + catNo);
            if (catNo == 3) {
                c = db.rawQuery(
                        "SELECT * from category1 where chestNo = "
                                + chestNo, null);
                c.moveToFirst();
                for (int i = 1; i <= catNo; i++) {
                    individual_lap_timings.add(c.getLong(i));
                }

            } else if (catNo == 5) {
                c = db.rawQuery(
                        "SELECT * from category2 where chestNo = "
                                + chestNo, null);
                c.moveToFirst();
                for (int i = 1; i <= catNo; i++) {
                    individual_lap_timings.add(c.getLong(i));
                }

            } else if (catNo == 10) {
                c = db.rawQuery(
                        "SELECT * from category3 where chestNo = "
                                + chestNo, null);
                c.moveToFirst();
                for (int i = 1; i <= catNo; i++) {
                    individual_lap_timings.add(c.getLong(i));
                }

            }
        } catch (Exception e) {
                // body
        }
        return individual_lap_timings;
    }

    public ArrayList<Participant> getResult3() {
        ArrayList<Participant> result3 = new ArrayList<Participant>();
        try {
            Cursor c = null;
            SQLiteDatabase db = this.getReadableDatabase();
            c = db.rawQuery("SELECT * from category1", null);
            while (c.moveToNext()) {
                int chestNo = c.getInt(c.getColumnIndex("chestNo"));
                Participant participant = new Participant(chestNo);
                participant.setLapTimes(individualResult(chestNo));
                result3.add(participant);
            }
            Log.d("DB", "Participants read cat 3:" +c.getCount());
            c.close();
        } catch (Exception e) {
            // body
        }
        return result3;
    }

    public ArrayList<Participant> getResult5() {
        ArrayList<Participant> result5 = new ArrayList<Participant>();
        try {
            Cursor c = null;
            SQLiteDatabase db = this.getReadableDatabase();
            c = db.rawQuery("SELECT * from category2", null);
            while (c.moveToNext()) {
                int chestNo = c.getInt(c.getColumnIndex("chestNo"));
                Participant participant = new Participant(chestNo);
                participant.setLapTimes(individualResult(chestNo));
                result5.add(participant);
            }
            Log.d("DB", "Participants read cat 2:" +c.getCount());
            c.close();
        } catch (Exception e) {
            // body
        }
        return result5;
    }

    public ArrayList<Participant> getResult10() {
        ArrayList<Participant> result10 = new ArrayList<Participant>();
        try {
            Cursor c = null;
            SQLiteDatabase db = this.getReadableDatabase();
            c = db.rawQuery("SELECT * from category3", null);
            while (c.moveToNext()) {
                int chestNo = c.getInt(c.getColumnIndex("chestNo"));
                Participant participant = new Participant(chestNo);
                participant.setLapTimes(individualResult(chestNo));
                result10.add(participant);
            }
            Log.d("DB", "Participants read cat 3:" +c.getCount());
            c.close();
        } catch (Exception e) {
            // body
        }
        return result10;
    }

}