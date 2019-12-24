package com.example.markshim.tvprogram.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * This is for program detail information
 *
 * Created by Mark Shim on 2018-01-17.
 */

public class Program implements Serializable, Comparable<Program> {
    /**
     * program id
     */
    @SerializedName("id")
    @Expose
    private Integer id;
    /**
     * program title
     */
    @SerializedName("title")
    @Expose
    private String title;
    /**
     * program start time
     */
    @SerializedName("startTime")
    @Expose
    private Long startTime;
    /**
     * program duration
     */
    @SerializedName("duration")
    @Expose
    private Integer duration;
    /**
     * program poster url
     */
    @SerializedName("poster")
    @Expose
    private String poster;
    /**
     * booking view text in item_program.xml
     */
    @SerializedName("bookingView")
    @Expose
    private String bookingView;
    /**
     * program channel info
     */
    @SerializedName("channel")
    @Expose
    private Integer channel;
    /**
     * date of program info in item_program.xml
     */
    @SerializedName("dateView")
    @Expose
    private String dateView;
    /**
     * running time of program info in item_program.xml
     */
    @SerializedName("runningTimeView")
    @Expose
    private String runningTimeView;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getBookingView() {
        return bookingView;
    }

    public void setBookingView(String bookingView) {
        this.bookingView = bookingView;
    }

    public Integer getChannel() {
        return  this.channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public String getDateView() {
        return this.dateView;
    }

    public void setDateView(String dateView) {
        this.dateView = dateView;
    }

    public String getRunningTimeView() {
        return runningTimeView;
    }

    public void setRunningTimeView(String runningTimeView) {
        this.runningTimeView = runningTimeView;
    }

    @Override
    public int compareTo(@NonNull Program program) {
        return this.channel.compareTo(program.channel);
    }
}
