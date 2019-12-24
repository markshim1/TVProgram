package com.example.markshim.tvprogram.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Program Recording Manager
 * Created by mark.shim on 2018. 1. 18..
 */

public class RecordingDBManager {
    private static final String TAG = RecordingDBManager.class.getSimpleName();

    private static final String DB_RECORDING = "Recording.db";
    private static final String TABLE_RECORDING = "Recording";
    private static final int DB_VERSION = 1;

    private Context context;

    private static RecordingDBManager recordingDBManager = null;
    private SQLiteDatabase sqLiteDatabase = null;

    public static RecordingDBManager getInstance(Context context) {
        if (recordingDBManager == null) {
            recordingDBManager = new RecordingDBManager(context);
        }
        return recordingDBManager;
    }

    /**
     * Whenever user book for recording program, the information is inserted to DB.
     *
     *  program ID
     *  program Title
     *  program start time (epoch time)
     *  program end time (epoch time)
     *  program recording ID
     * @param context
     */
    private RecordingDBManager(Context context) {
        this.context = context;

        sqLiteDatabase = context.openOrCreateDatabase(DB_RECORDING, Context.MODE_PRIVATE, null);

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_RECORDING + "(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                            "id INTEGER, " +
                                            "title TEXT, " +
                                            "starttime INTEGER, " +
                                            "endtime INTEGER, " +
                                            "recordingId INTEGER); ");
    }

    /**
     *  recording information is inserted to DB
     *
     * @param addRowValue
     * @return
     */
    private long insert(ContentValues addRowValue) {
        return sqLiteDatabase.insert(TABLE_RECORDING, null, addRowValue);
    }

    /**
     * delete all record
     */
    public void deleteAll() {
        sqLiteDatabase.execSQL("delete from " + TABLE_RECORDING);
    }

    /**
     * whenever user cancel booking recording program, the information must be deleted from DB too.
     * The information can be deleted by recording ID
     *
     * @param recordingId
     * @return
     */
    public int deleteWithRecordingID(int recordingId) {
        return sqLiteDatabase.delete(TABLE_RECORDING, " recordingId = ?", new String[] {String.valueOf(recordingId)});
    }

    /**
     * Whenever user book for recording program, the information is inserted to DB.
     *
     * @param id
     * @param recordingId
     * @param startTime
     * @param duration
     * @param title
     * @return
     */
    public long setProgramBooked(int id, int recordingId, long startTime, int duration, String title) {
        Log.i(TAG, "setProgramBooked");
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("title", title);
        contentValues.put("recordingId" , recordingId);
        contentValues.put("starttime", startTime);
        contentValues.put("endtime", startTime+duration);

        return insert(contentValues);
    }

    /**
     * With program ID, we can see whether the program is already booked for recording or not.
     *
     * @param programId
     * @return
     */
    public boolean isProgramBooked(int programId) {
        String[] columns = new String[] {"id"};

        Cursor cursor = sqLiteDatabase.query(TABLE_RECORDING, columns, " id = ?", new String[] {String.valueOf(programId)}, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * With Program ID, we can get the recording ID of the program.
     *
     * @param programId
     * @return
     */
    public Integer getRecordingId(int programId) {
        String[] columns = new String[] {"recordingId"};

        Cursor cursor = sqLiteDatabase.query(TABLE_RECORDING, columns, " id = ?", new String[] {String.valueOf(programId)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            return cursor.getInt(0);
        } else {
            return null;
        }
    }

    /**
     * With start time and end time of the program, we can check whether there is any program, which is already booked for recording,
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public Bundle getConflictProgram(long startTime, long endTime) {
        String title = null;
        int recordingID;
        Bundle bundle = null;

        String[] columns = new String[] {"recordingId", "title", "starttime", "endtime"};
        int sTime;
        int eTime;
        Cursor cursor = sqLiteDatabase.query(TABLE_RECORDING, columns, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                sTime = cursor.getInt(2);
                eTime = cursor.getInt(3);
                if ((startTime >= sTime && startTime <= eTime) ||
                        (endTime >= sTime && endTime <= eTime) ||
                        (startTime <= sTime && endTime >= eTime)) {
                    title = cursor.getString(1);
                    recordingID = cursor.getInt(0);
                    bundle = new Bundle();
                    bundle.putString("title", title);
                    bundle.putInt("recordingid", recordingID);
                    Log.e(TAG, "title = " + title + ", recordingID = " + recordingID);
                    break;
                }
            } while (cursor.moveToNext());
        }
        return bundle;
    }
}
