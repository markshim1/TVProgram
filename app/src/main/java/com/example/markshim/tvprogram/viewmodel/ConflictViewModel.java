package com.example.markshim.tvprogram.viewmodel;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.markshim.tvprogram.ProgramApplication;
import com.example.markshim.tvprogram.model.BookingResponse;
import com.example.markshim.tvprogram.model.Program;
import com.example.markshim.tvprogram.model.ProgramService;
import com.example.markshim.tvprogram.model.db.RecordingDBManager;
import com.example.markshim.tvprogram.MainActivity;

import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * For conflict activity
 *
 * Created by Mark Shim on 2018-01-21.
 */

public class ConflictViewModel extends Observable {
    private static final String TAG = ConflictViewModel.class.getSimpleName();
    private Context context;
    private Program program;
    private RecordingDBManager recordingDBManager;
    private String conflictTitle;
    private int conflictRecordingId;
    private ProgramService programService;

    public ConflictViewModel (Context context, Program program, ProgramService programService) {
        this.context = context;
        this.program = program;
        recordingDBManager = RecordingDBManager.getInstance(context);
        setConflictInfo(program.getStartTime(), program.getStartTime() + program.getDuration());
        this.programService = programService;
    }

    private void setConflictInfo(long startTime, long endTime) {
        Bundle bundle = recordingDBManager.getConflictProgram(startTime, endTime);
        if (bundle != null) {
            conflictTitle = bundle.getString("title");
            conflictRecordingId = bundle.getInt("recordingid");
        }
    }

    public String getConflictProgramTitle() {
        return conflictTitle;
    }

    public int getConflictProgramRecordingId() {
        return conflictRecordingId;
    }

    public void onCancelRecording(View view) {
        Log.d(TAG, "onCancelClick (recording id) = " +conflictRecordingId );
        cancelRecording(conflictRecordingId);
    }

    private void cancelRecording(final int recording_id) {
        ProgramApplication programApplication = ProgramApplication.create(context);

        Disposable disposable = programService.cancelRecord(String.valueOf(recording_id))
                .subscribeOn(programApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BookingResponse>() {

                    @Override
                    public void onNext(BookingResponse bookingResponse) {
                        int result = recordingDBManager.deleteWithRecordingID(recording_id);
                        context.startActivity(MainActivity.getStartIntent(context));
                    }

                    /**
                     * Assume that always get successful response (200 OK)
                     * No need to implement
                     */
                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError = " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
