package com.example.markshim.tvprogram;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.markshim.tvprogram.model.db.RecordingDBManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNull;

/**
 * Created by mark.shim on 2018. 1. 18..
 */
@RunWith(AndroidJUnit4.class)
public class RecordingDBManagerTest {
    private static final String TAG = RecordingDBManagerTest.class.getSimpleName();
    private RecordingDBManager recordingDBManager;


    @Before
    public void setUp() {
        Log.e(TAG, "setUp");
        recordingDBManager = RecordingDBManager.getInstance(InstrumentationRegistry.getTargetContext());
        recordingDBManager.deleteAll();
        recordingDBManager.setProgramBooked(3, 3456, 1516986000, 3600, "National Treeasure");
    }

    @After
    public void finish() {
        recordingDBManager.deleteAll();
    }

    @Test
    public void setProgramBookedTest() {
        long value = recordingDBManager.setProgramBooked(3, 3456, 1516986000, 3600, "National Treeasure");
        assertNotSame(-1, value);
    }

    @Test
    public void isProgramBookedSuccessTest() {
        assertTrue(recordingDBManager.isProgramBooked(3));
    }

    @Test
    public void isProgramBookedFailTest() {
        assertFalse(recordingDBManager.isProgramBooked(2));
    }

    @Test
    public void getRecordingIdTest() {
        assertEquals((Integer)3456, recordingDBManager.getRecordingId(3));
    }

    @Test
    public void getConflictProgramSuccessTest() {
        Bundle bundle = recordingDBManager.getConflictProgram(1516986000, 1516987000);
        assertNotNull(bundle);
    }

    @Test
    public void getConflictProgramFailTest() {
        Bundle bundle = recordingDBManager.getConflictProgram(1516984000, 1516985000);
        assertNull(bundle);
    }

    @Test
    public void getConflictProgramNameSuccessTest() {
        Bundle bundle = recordingDBManager.getConflictProgram(1516986000, 1516987000);
        String name = bundle.getString("title");
        assertEquals("National Treeasure", name);
    }
}
