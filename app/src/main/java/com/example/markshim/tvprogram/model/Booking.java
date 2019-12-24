package com.example.markshim.tvprogram.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * This is for CanceiViewModel
 * *
 * Created by Mark Shim on 2018-01-20.
 */

public class Booking implements Serializable{
    /**
     * recordingId is needed for cancellation of booking
     */
    @SerializedName("recordingId")
    @Expose
    private Integer recordingId;

    /**
     * title is needed for cancel view
     * show program title
     */
    @SerializedName("title")
    @Expose
    private String title;

    public void setRecordingId(Integer recordingId) {
        this.recordingId = recordingId;
    }

    public Integer getRecordingId() {
        return recordingId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
