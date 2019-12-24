package com.example.markshim.tvprogram;

import android.app.Application;
import android.content.Context;

import com.example.markshim.tvprogram.di.DaggerServerRequestComponent;
import com.example.markshim.tvprogram.di.ServerRequestComponent;
import com.example.markshim.tvprogram.model.ProgramFactory;
import com.example.markshim.tvprogram.model.ProgramService;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Mark Shim on 2018-01-17.
 */

public class ProgramApplication extends Application {

    private Scheduler scheduler;

    private static ProgramApplication get (Context context) {
        return (ProgramApplication) context.getApplicationContext();
    }

    public static ProgramApplication create (Context context) {
        return ProgramApplication.get(context);
    }

    public Scheduler subscribeScheduler() {
        if (scheduler == null) {
            scheduler = Schedulers.io();
        }

        return scheduler;
    }
}
