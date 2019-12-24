package com.example.markshim.tvprogram.viewmodel;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.markshim.tvprogram.ProgramApplication;
import com.example.markshim.tvprogram.R;
import com.example.markshim.tvprogram.model.Booking;
import com.example.markshim.tvprogram.model.BookingResponse;
import com.example.markshim.tvprogram.model.Program;
import com.example.markshim.tvprogram.model.ProgramService;
import com.example.markshim.tvprogram.model.db.RecordingDBManager;
import com.example.markshim.tvprogram.view.CancelFragment;
import com.example.markshim.tvprogram.view.ConflictActivity;

import java.net.HttpURLConnection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * Created by Mark Shim on 2018-01-17.
 */

public class ItemProgramViewModel extends BaseObservable {
    private static final String TAG = ItemProgramViewModel.class.getSimpleName();
    private Context context;
    private Program program;
    private RecordingDBManager dbManager;
    private View view;
    private ProgramService programService;
    private FragmentManager fragmentManager;

    public ItemProgramViewModel(Context context, Program program, ProgramService programService, FragmentManager fragmentManager) {
        this.context = context;
        this.program = program;
        this.programService = programService;
        this.fragmentManager = fragmentManager;
        dbManager = RecordingDBManager.getInstance(context);
    }

    public String getTitle() {
        return program.getTitle();
    }

    public Integer getDuration() {
        return program.getDuration();
    }

    public String getRunningTimeView() {
        return program.getRunningTimeView();
    }

    public String getStartTime() {
        return String.valueOf(program.getStartTime());
    }

    public String getId() {
        return String.valueOf(program.getId());
    }

    public String getPoster() {
        return program.getPoster();
    }

    public String getDateView() {
        return program.getDateView();
    }

    @BindingAdapter("imageUrl") public static void setImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    public void setProgram(Program program) {
        this.program = program;
        notifyChange();
    }

    public String getBookingView() {
        return program.getBookingView();
    }

    public String getChannel() {
        return String.valueOf(program.getChannel());
    }

    public void onItemClick(View view) {
        this.view = view;
        int id = program.getId();
        if (isBooked(id)) {
            showCancel(id);
        } else {
            bookProcess(program);
        }
    }

    private void showCancel(int id) {
        Log.w(TAG, "Already booked recording id = " + dbManager.getRecordingId(id));
        Bundle bundle = getBookingBundle(id);
        CancelFragment cancelFragment = getCancelFragment(bundle);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack("ProgramFragment");
        fragmentTransaction.replace(R.id.content, cancelFragment);
        fragmentTransaction.commit();
    }

    private CancelFragment getCancelFragment(Bundle bundle) {
        CancelFragment cancelFragment = new CancelFragment();
        cancelFragment.setArguments(bundle);
        return cancelFragment;
    }

    private Bundle getBookingBundle(int id) {
        Booking booking = new Booking();
        booking.setTitle(program.getTitle());
        booking.setRecordingId(dbManager.getRecordingId(id));

        Bundle bundle = new Bundle();
        bundle.putSerializable("BOOKING", booking);
        return bundle;
    }


    private boolean isBooked(int id) {
        return dbManager.isProgramBooked(id);
    }

    private void bookProcess(final Program program) {
        ProgramApplication programApplication = ProgramApplication.create(context);

        Disposable disposable = programService.requestRecord(String.valueOf(program.getChannel()), String.valueOf(program.getId()))
                .subscribeOn(programApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(disposableObserver);
    }

    DisposableObserver<BookingResponse> disposableObserver = new DisposableObserver<BookingResponse>() {

        @Override
        public void onNext(BookingResponse bookingResponse) {
            int recordingId = bookingResponse.getBooking().getRecordingId();
            Log.d(TAG, "onNext!! Booked id = " + program.getId() + ", recording id = " +recordingId);

            dbManager.setProgramBooked(program.getId(), recordingId, program.getStartTime(), program.getDuration(),  program.getTitle());
            program.setBookingView("Booked for recording!");
            notifyChange();
        }

        /**
         * if error code is 409, then booking recording program is conflict with previous booked program.
         * @param e
         */
        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "onError = " + e.getMessage());
            int errCode;
            if (e instanceof HttpException) {
                HttpException httpException = (HttpException) e;
                errCode = ((HttpException) e).code();
                Log.e(TAG, "http exception = " + errCode);
                if (errCode == HttpURLConnection.HTTP_CONFLICT) {
                    Log.e(TAG, "Conflict");
                    processConflict(program);
                }
            }
        }

        @Override
        public void onComplete() {
        }
    };

    private void processConflict(Program program) {
        context.startActivity(ConflictActivity.getStartIntent(context, program));
    }
}

