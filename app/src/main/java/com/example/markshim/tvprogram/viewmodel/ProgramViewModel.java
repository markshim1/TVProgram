package com.example.markshim.tvprogram.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;

import com.example.markshim.tvprogram.ProgramApplication;
import com.example.markshim.tvprogram.model.Program;
import com.example.markshim.tvprogram.model.ProgramResponse;
import com.example.markshim.tvprogram.model.ProgramService;
import com.example.markshim.tvprogram.model.db.RecordingDBManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * This is main program view model
 * It has a list of ItemProgramViewModel
 *
 * Created by Mark Shim on 2018-01-17.
 */

public class ProgramViewModel extends Observable {
    private static final String TAG = ProgramViewModel.class.getSimpleName();
    private Context context;
    private List<Program> programList;
    public ObservableInt progress;
    public ObservableInt programRecycler;
    private RecordingDBManager recordingDBManager;
    private ProgramService programService;

    public ProgramViewModel(Context context, ProgramService programService) {
        this.context = context;
        this.programList = new ArrayList<>();
        progress = new ObservableInt(View.VISIBLE);
        programRecycler = new ObservableInt(View.GONE);
        recordingDBManager = RecordingDBManager.getInstance(context);
        this.programService = programService;
    }

    public void init() {
        programList = new ArrayList<>();
    }

    /**
     * request program list of channel for next 6 hours
     *
     * @param ch : program channel
     */
    public void requestPrograms(final int ch) {
        ProgramApplication programApplication = ProgramApplication.create(context);

        Disposable disposable = programService.getProgramList(String.valueOf(ch))
                .subscribeOn(programApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ProgramResponse>() {

                    @Override
                    public void onNext(ProgramResponse programResponse) {
                        Log.d(TAG, "onNext!! ch = " + ch);
                        List<Program> programList = programResponse.getProgramList(ch);
                        setBooked(programList);
                        changeProgramDataSet(programList);
                        progress.set(View.GONE);
                        programRecycler.set(View.VISIBLE);
                    }

                    /**
                     * Assume that always get successful response (200 OK)
                     * No need to implement
                     */
                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError!!");
                        programRecycler.set(View.GONE);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete!!");
                    }
                });
    }

    private void changeProgramDataSet(List<Program> programs) {
        programList.addAll(programs);
        Collections.sort(programList);
        setChanged();
        notifyObservers();
    }

    public List<Program> getProgramList() {
        return programList;
    }

    /**
     * whenever the program is booked for recording, view should be changed.
     * "Book!" => "Booked for recording!"
     *
     * @param programList
     */
    private void setBooked(List<Program> programList) {
        if (programList != null && programList.size()>0) {
            for (int i=0; i<programList.size(); i++) {
                if (recordingDBManager.isProgramBooked(programList.get(i).getId())) {
                    programList.get(i).setBookingView("Booked for recording!");
                }
            }
        }
    }
}
