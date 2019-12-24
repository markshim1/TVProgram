package com.example.markshim.tvprogram.viewmodel;

import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.markshim.tvprogram.ProgramApplication;
import com.example.markshim.tvprogram.model.Booking;
import com.example.markshim.tvprogram.model.BookingResponse;
import com.example.markshim.tvprogram.model.ProgramService;
import com.example.markshim.tvprogram.model.db.RecordingDBManager;
import com.example.markshim.tvprogram.MainActivity;

import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * For Cancel Activity
 *
 * Created by Mark Shim on 2018-01-20.
 */

public class CancelViewModel extends Observable {
    private static final String TAG = CancelViewModel.class.getSimpleName();
    private Context context;
    private Booking booking;
    private RecordingDBManager dbManager = null;
    private ProgramService programService;
    private FragmentManager fragmentManager;

    public CancelViewModel(Context context, Booking booking, ProgramService programService, FragmentManager fragmentManager) {
        this.context = context;
        this.booking = booking;
        this.programService = programService;
        this.fragmentManager = fragmentManager;
    }

    public void onCancelClick(View view) {
        Log.e(TAG, "onCancelClick");
        cancelRecording(booking.getRecordingId());
    }

    public void onBackClick(View view) {
        fragmentManager.popBackStack();
    }

    public String getTitle() {
        return booking.getTitle();
    }

    private void cancelRecording(final int recording_id) {
        if (dbManager == null) {
            dbManager = RecordingDBManager.getInstance(context);
        }
        ProgramApplication programApplication = ProgramApplication.create(context);

        Disposable disposable = programService.cancelRecord(String.valueOf(recording_id))
                .subscribeOn(programApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BookingResponse>() {

                    @Override
                    public void onNext(BookingResponse bookingResponse) {
                        Log.e(TAG, "onNext!!");
                        int result = dbManager.deleteWithRecordingID(recording_id);
                        Log.e(TAG, "db deleteWithRecordingID -> " + result);
                        fragmentManager.popBackStack();
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
