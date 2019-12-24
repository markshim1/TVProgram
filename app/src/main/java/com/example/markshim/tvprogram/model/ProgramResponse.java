package com.example.markshim.tvprogram.model;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Mark Shim on 2018-01-17.
 */

public class ProgramResponse {
    private static final String TAG = ProgramResponse.class.getSimpleName();
    @SerializedName("results") private List<Program> programList;

    /**
     * whenever program list is updated, it generates some information which wil show on item_program.xml
     *
     * @param ch
     * @return
     */
    public List<Program> getProgramList(int ch) {
        SimpleDateFormat day_format = new SimpleDateFormat("MMM dd ", Locale.ENGLISH);
        SimpleDateFormat time_format = new SimpleDateFormat("HH:mm a", Locale.ENGLISH);

        long start_mills, end_mills;
        for (int i=0; i<programList.size(); i++) {
            start_mills = programList.get(i).getStartTime() * 1000;
            end_mills = start_mills + (programList.get(i).getDuration()*1000);

            programList.get(i).setDateView(day_format.format(new Date(start_mills)));
            programList.get(i).setRunningTimeView(time_format.format(new Date(start_mills)) + " ~ " + time_format.format(new Date(end_mills)));
            programList.get(i).setChannel(ch);
            programList.get(i).setBookingView("Book!");
        }

        return programList;
    }

    public void setProgramList(List<Program> programList) {
        this.programList = programList;
    }
}
