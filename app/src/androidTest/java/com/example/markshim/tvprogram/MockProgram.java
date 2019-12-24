package com.example.markshim.tvprogram;

import com.example.markshim.tvprogram.model.Program;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark Shim on 2018-01-22.
 */

public class MockProgram {
    private static final int id = 1234;
    private static final String title = "National Treasure";
    private static final long starttime = 1516986000;
    private static final int duration = 3600;
    private static final String poster = "http://www.xxx.com/post.jpg" ;
    private static final String booked ="Booked for recording";
    private static final int channel = 1;
    private static final String starttimeview ="Jan 27";
    private static final String runningtimeview ="02:00 AM ~ 03:00 AM";

    public static Program getProgram() {
        Program program = new Program();
        program.setId(id);
        program.setTitle(title);
        program.setStartTime(starttime);
        program.setDuration(duration);
        program.setPoster(poster);
        program.setBookingView(booked);
        program.setChannel(channel);
        program.setDateView(starttimeview);
        program.setRunningTimeView(runningtimeview);
        return program;
    }

    public static List<Program> getProgramList() {
        List<Program> programList = new ArrayList<>();
        for (int i=0; i<5; i++) {
            programList.add(getProgram());
        }
        return programList;
    }
}
